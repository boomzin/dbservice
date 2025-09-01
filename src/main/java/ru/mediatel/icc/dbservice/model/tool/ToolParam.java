package ru.mediatel.icc.dbservice.model.tool;

public record ToolParam(
        String name,
        String description,
        String type,
        boolean required
) {}
