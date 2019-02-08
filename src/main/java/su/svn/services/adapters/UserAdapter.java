/*
 * UserAdapter.java
 * This file was last modified at 2019-02-08 22:20 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services.adapters;

import su.svn.models.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class UserAdapter implements JsonbAdapter<User, JsonObject>
{
    @Override
    public JsonObject adaptToJson(User user) throws Exception
    {
        return Json.createObjectBuilder()
            .add("id", user.getId())
            .add("name", user.getName())
            .add("description", user.getDescription())
            .build();
    }

    @Override
    public User adaptFromJson(JsonObject json) throws Exception
    {
        User user = new User();
        user.setId((long) json.getInt("id"));
        user.setName(json.getString("name"));
        System.err.println("json = " + json);
        user.setDescription(json.getString("description", null));
        return user;
    }
}
