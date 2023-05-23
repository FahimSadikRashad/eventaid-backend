package com.example.eventlybackend.evently.exception;

/**
 * The ResourceNotFoundException is an exception that is thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    /**
     * Constructs a new ResourceNotFoundException with the specified resource name, field name, and field value.
     *
     * @param resourceName The name of the resource that was not found.
     * @param fieldName    The name of the field associated with the resource.
     * @param fieldValue   The value of the field associated with the resource.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Get the name of the resource that was not found.
     *
     * @return The name of the resource.
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Get the name of the field associated with the resource.
     *
     * @return The name of the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Get the value of the field associated with the resource.
     *
     * @return The value of the field.
     */
    public long getFieldValue() {
        return fieldValue;
    }
}
