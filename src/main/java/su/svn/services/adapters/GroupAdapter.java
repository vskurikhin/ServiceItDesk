/*
 * GroupAdapter.java
 * This file was last modified at 2019-02-08 21:38 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services.adapters;

import su.svn.models.Group;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class GroupAdapter implements JsonbAdapter<Group, JsonObject>
{
    @Override
    public JsonObject adaptToJson(Group group) throws Exception
    {
        return Json.createObjectBuilder()
            .add("id", group.getId())
            .add("name", group.getName())
            .add("description", group.getDescription())
            .build();
    }

    @Override
    public Group adaptFromJson(JsonObject json) throws Exception
    {
        Group group = new Group();
        group.setId((long) json.getInt("id"));
        group.setName(json.getString("name"));
        group.setDescription(json.isNull("description") ? null : json.getString("description"));
        return group;
    }
}
