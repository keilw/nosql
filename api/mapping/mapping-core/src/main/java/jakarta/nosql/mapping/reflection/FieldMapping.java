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
package jakarta.nosql.mapping.reflection;


import jakarta.nosql.Value;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Id;
import jakarta.nosql.mapping.AttributeConverter;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * This class represents the information from {@link Field}.
 * The strategy is do cache in all fields in a class to either read and writer faster from Field
 */
public interface FieldMapping {

    /**
     * Return the type of the field
     *
     * @return the {@link FieldType}
     */
    FieldType getType();

    /**
     * The {@link Field}
     *
     * @return the field
     */
    Field getNativeField();

    /**
     * Reads the field using {@link FieldReader}
     *
     * @param bean the bean
     * @return the property value
     * @throws NullPointerException when bean is null
     */
    Object read(Object bean);

    /**
     * Writes the field using {@link java.io.FileWriter}
     *
     * @param bean  the bean
     * @param value the value to write
     * @throws NullPointerException when there is null parameter
     */
    void write(Object bean, Object value);

    /**
     * Returns the name of the field that can be eiher the field name
     * or {@link Column#value()}
     *
     * @return the name
     */
    String getName();

    /**
     * Returns the Java Fields name.
     * {@link Field#getName()}
     *
     * @return The Java Field name {@link Field#getName()}
     */
    String getFieldName();


    /**
     * Returns the object from the field type
     *
     * @param value the value {@link Value}
     * @return the instance from the field type
     */
    Object getValue(Value value);

    /**
     * Returns true is the field is annotated with {@link Id}
     *
     * @return true is annotated with {@link Id}
     */
    boolean isId();

    /**
     * Returns the converter class
     *
     * @param <T> the Converter
     * @return the converter if present
     */
    <T extends AttributeConverter> Optional<Class<? extends AttributeConverter>> getConverter();


}
