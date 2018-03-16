/*
 * Copyright (C) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.client.promise;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.JavaScriptObject;
import elemental2.dom.DomGlobal;
import elemental2.promise.Promise;
import elemental2.promise.Promise.PromiseExecutorCallbackFn.RejectCallbackFn;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;

import static org.uberfire.client.promise.PromisePolyfillBootstrapper.ensurePromiseApiIsAvailable;

@Dependent
public class Promises {

    @PostConstruct
    public void init() {
        ensurePromiseApiIsAvailable();
    }

    // Reducers

    /**
     * Returns a resolved Promise when every Promise in the list is resolved.
     * If any Promise is rejected, the resulting Promise will be rejected.
     */
    @SafeVarargs
    public final <O> Promise<O> all(final Promise<O>... promises) {
        return Arrays.stream(promises).reduce(resolve(), (p1, p2) -> p1.then(ignore -> p2));
    }

    /**
     * Maps the objects to Promises using the provided function then behaves just like {@link Promises#all}.
     */
    public <T, O> Promise<O> all(final List<T> objects, final Function<T, Promise<O>> f) {
        return objects.stream().map(f).reduce(resolve(), (p1, p2) -> p1.then(ignore -> p2));
    }

    /**
     * Maps the objects to Promises using the provided function but only execute the Promises when the
     * previous Promise is resolved. If a rejection occurs in the middle of the chain, the remaining
     * Promises are not executed and the resulting Promise is rejected.
     */
    public <T, O> Promise<O> reduceLazily(final List<T> objects,
                                          final Function<T, Promise<O>> f) {
        return objects.stream()
                .<Supplier<Promise<O>>>
                        map(o -> () -> f.apply(o))
                .<Supplier<Promise<O>>>
                        reduce(this::resolve,
                               (p1, p2) -> () -> p1.get().then(ignore -> p2.get())
                )
                .get();
    }

    /**
     * Behaves just like {@link Promises#reduceLazily} but exposes a reference to the Promise chain as a
     * parameter to the mapping function.
     */
    public <T, O> Promise<O> reduceLazilyChaining(final List<T> objects,
                                                  final BiFunction<Supplier<Promise<O>>, T, Promise<O>> f) {

        return objects.stream()
                .<Function<Supplier<Promise<O>>, Supplier<Promise<O>>>>
                        map(o -> next -> () -> f.apply(next, o))
                .<Function<Supplier<Promise<O>>, Supplier<Promise<O>>>>
                        reduce(next -> this::resolve,
                               (p1, p2) -> uberNext -> () -> {
                                   final Supplier<Promise<O>> next = p2.apply(uberNext);
                                   final Supplier<Promise<O>> chain = () -> next.get().then(ignore -> uberNext.get());
                                   return p1.apply(chain).get().then(ignore -> next.get());
                               }
                )
                .apply(this::resolve).get();
    }

    // Callers

    /**
     * Promisifies a {@link Caller} remote call. If an exception is thrown inside the call function, the
     * resulting Promise is rejected with a {@link Promises.Error} instance.
     */
    public <T, S> Promise<S> promisify(final Caller<T> caller,
                                       final Function<T, S> call) {

        return create((resolve, reject) -> call.apply(caller.call(
                (RemoteCallback<S>) resolve::onInvoke,
                defaultErrorCallback(reject))));
    }

    /**
     * Promisifies a {@link Caller} remote call. If an exception is thrown inside the call function, the
     * resulting Promise is rejected with a {@link Promises.Error} instance.
     */
    public <T, S> Promise<S> promisify(final Caller<T> caller,
                                       final Consumer<T> call) {

        return create((resolve, reject) -> call.accept(caller.call(
                (RemoteCallback<S>) resolve::onInvoke,
                defaultErrorCallback(reject))));
    }

    private <M> ErrorCallback<M> defaultErrorCallback(final RejectCallbackFn reject) {
        return (final M o, final Throwable throwable) -> {
            reject.onInvoke(new Error<>(o, throwable));
            return true;
        };
    }

    /**
     * To be used inside {@link Promise#catch_} blocks. Decides whether to process a RuntimeException that
     * caused a prior Promise rejection or to process an expected object rejected by a prior Promise.
     */
    @SuppressWarnings("unchecked")
    public <V, T> Promise<T> catchOrExecute(final Object o,
                                            final Function<RuntimeException, Promise<T>> c,
                                            final Function<V, Promise<T>> f) {

        if (o instanceof JavaScriptObject) {
            // A RuntimeException occurred inside a promise and was transformed in a JavaScriptObject
            DomGlobal.console.error(o);
            return c.apply(new RuntimeException(o.toString()));
        } else if (o instanceof RuntimeException) {
            return c.apply((RuntimeException) o);
        } else {
            return f.apply((V) o);
        }
    }

    public <T> Promise<T> resolve() {
        return resolve(null);
    }

    public <T> Promise<T> resolve(final T object) {
        return create((resolve, reject) -> resolve.onInvoke(object));
    }

    public <T> Promise<T> reject(final Object object) {
        return create((resolve, reject) -> reject.onInvoke(object));
    }

    public <T> Promise<T> create(final Promise.PromiseExecutorCallbackFn<T> executor) {
        return new Promise<>(executor);
    }

    public static class Error<T> {

        private final T o;

        private final Throwable throwable;

        public Error(final T o, final Throwable throwable) {
            this.o = o;
            this.throwable = throwable;
        }

        public T getObject() {
            return o;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}
