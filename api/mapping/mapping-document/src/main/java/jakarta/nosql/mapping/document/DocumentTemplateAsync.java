/*
 * Copyright (c) 2019 Otavio Santana and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package jakarta.nosql.mapping.document;


import jakarta.nosql.NonUniqueResultException;
import jakarta.nosql.document.DocumentDeleteQuery;
import jakarta.nosql.document.DocumentQuery;
import jakarta.nosql.mapping.IdNotFoundException;
import jakarta.nosql.mapping.PreparedStatementAsync;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * This interface that represents the common operation between an entity and DocumentCollectionEntity.
 *
 * @see jakarta.nosql.document.DocumentCollectionManagerAsync
 */
public interface DocumentTemplateAsync {

    /**
     * Saves an entity asynchronously
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when entity are null
     */
    <T> void insert(T entity);

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @param ttl    the time to live
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either entity or ttl are null
     */
    <T> void insert(T entity, Duration ttl);

    /**
     * Saves entities asynchronously, by default it's just run for each saving using
     * {@link DocumentTemplateAsync#insert(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when entities is null
     */
    default <T> void insert(Iterable<T> entities) {
        requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::insert);
    }

    /**
     * Saves entities asynchronously with time to live, by default it's just run for each saving using
     * {@link DocumentTemplateAsync#insert(Object)} (Object, Duration)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @param ttl      time to live
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either entities or ttl are null
     */
    default <T> void insert(Iterable<T> entities, Duration ttl) {
        requireNonNull(entities, "entities is required");
        requireNonNull(ttl, "ttl is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(d -> insert(d, ttl));
    }

    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either entity or callBack are null
     */
    <T> void insert(T entity, Consumer<T> callBack);

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity   entity to be saved
     * @param ttl      time to live
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either entity or ttl or callBack are null
     */
    <T> void insert(T entity, Duration ttl, Consumer<T> callBack);

    /**
     * Updates an entity asynchronously
     *
     * @param entity   entity to be updated
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the updated entity within parametersa
     * @param <T>      the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either entity or callback are null
     */
    <T> void update(T entity, Consumer<T> callBack);


    /**
     * Updates an entity asynchronously
     *
     * @param entity entity to be updated
     * @param <T>    the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when entity is null
     */
    <T> void update(T entity);

    /**
     * Updates entities asynchronously, by default it's just run for each saving using
     * {@link DocumentTemplate#update(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when entities is null
     */
    default <T> void update(Iterable<T> entities) {
        requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::update);
    }

    /**
     * Deletes an entity asynchronously
     *
     * @param query query to delete an entity
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when query is null
     */
    void delete(DocumentDeleteQuery query);


    /**
     * Deletes an entity asynchronously
     *
     * @param query    query to delete an entity
     * @param callback the callback, when the process is finished will call this instance returning
     *                 the null within parameters
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to delete asynchronous
     * @throws NullPointerException                            when either query or callback are null
     */
    void delete(DocumentDeleteQuery query, Consumer<Void> callback);

    /**
     * Finds entities from query asynchronously
     *
     * @param query    query to select entities
     * @param <T>      the instance type
     * @param callback the callback, when the process is finished will call this instance returning
     *                 the result of query within parameters
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either query or callback are null
     */
    <T> void select(DocumentQuery query, Consumer<Stream<T>> callback);

    /**
     * Executes a query then bring the result as a {@link Stream}
     *
     * @param callback the callback, when the process is finished will call this instance returning
     * @param query    the query
     * @param <T>      the entity type
     * @throws NullPointerException when the query is null
     */
    <T> void query(String query, Consumer<Stream<T>> callback);

    /**
     * Executes a query then bring the result as a unique result
     *
     * @param callback the callback, when the process is finished will call this instance returning
     * @param query    the query
     * @param <T>      the entity type
     * @throws NullPointerException     when the query is null
     * @throws NonUniqueResultException if returns more than one result
     */
    <T> void singleResult(String query, Consumer<Optional<T>> callback);

    /**
     * Creates a {@link PreparedStatementAsync} from the query
     *
     * @param query the query
     * @return a {@link PreparedStatementAsync} instance
     * @throws NullPointerException when the query is null
     */
    PreparedStatementAsync prepare(String query);

    /**
     * Finds by Id.
     *
     * @param entityClass the entity class
     * @param id          the id value
     * @param <T>         the entity class type
     * @param <K>        the id type
     * @param callBack    the callBack
     * @throws NullPointerException                   when either the entityClass or id are null
     * @throws IdNotFoundException when the entityClass does not have the Id annotation
     */
    <T, K> void find(Class<T> entityClass, K id, Consumer<Optional<T>> callBack);

    /**
     * Deletes by Id.
     *
     * @param entityClass the entity class
     * @param id          the id value
     * @param <T>         the entity class type
     * @param <K>        the id type
     * @param callBack    the callBack
     * @throws NullPointerException                   when either the entityClass or id are null
     * @throws IdNotFoundException when the entityClass does not have the Id annotation
     */
    <T, K> void delete(Class<T> entityClass, K id, Consumer<Void> callBack);

    /**
     * Deletes by Id.
     *
     * @param entityClass the entity class
     * @param id          the id value
     * @param <T>         the entity class type
     * @param <K>        the id type
     * @throws NullPointerException                   when either the entityClass or id are null
     * @throws IdNotFoundException when the entityClass does not have the Id annotation
     */
    <T, K> void delete(Class<T> entityClass, K id);

    /**
     * Returns the number of elements from document collection
     *
     * @param documentCollection the document collection
     * @param callback           the callback with the response
     * @throws NullPointerException          when there is null parameter
     * @throws UnsupportedOperationException when the database dot not have support
     */
    void count(String documentCollection, Consumer<Long> callback);

    /**
     * Returns the number of elements from document collection
     *
     * @param <T>         the entity type
     * @param entityClass the document collection
     * @param callback    the callback with the response
     * @throws NullPointerException          when there is null parameter
     * @throws UnsupportedOperationException when the database dot not have support
     */
    <T> void count(Class<T> entityClass, Consumer<Long> callback);


    /**
     * Execute a query to consume an unique result
     *
     * @param query    the query
     * @param callBack the callback
     * @param <T>      the type
     * @throws jakarta.nosql.ExecuteAsyncQueryException when there is a async error
     * @throws UnsupportedOperationException                   when the database does not have support to insert asynchronous
     * @throws NullPointerException                            when either query or callback are null
     * @throws NonUniqueResultException                        when it returns more than one result
     */
    <T> void singleResult(DocumentQuery query, Consumer<Optional<T>> callBack);
}
