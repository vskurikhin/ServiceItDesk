/*
 * CreateTables.java
 * This file was last modified at 2019-02-18 21:09 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn;

/*
 * Created by VSkurikhin at 2019.
 */

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import su.svn.models.*;
import su.svn.utils.Authentication;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;

/**
 *
 * mvn clean compile dependency:copy-dependencies
 * createSchema.sh or createSchema.bat
 */

public class CreateTables
{
    public static final String DB_SCHEMA_DDL = "db-schema.hibernate5.ddl";

    public static Metadata getMetadata(StandardServiceRegistryBuilder builder)
    {
        StandardServiceRegistry registry = builder.build();
        MetadataSources sources = new MetadataSources(registry);

        sources.addAnnotatedClass(Group.class);
        sources.addAnnotatedClass(PrimaryGroup.class);
        sources.addAnnotatedClass(User.class);
        sources.addAnnotatedClass(ConfigurationType.class);
        sources.addAnnotatedClass(ConfigurationUnit.class);
        sources.addAnnotatedClass(Message.class);
        sources.addAnnotatedClass(Status.class);
        sources.addAnnotatedClass(Task.class);
        sources.addAnnotatedClass(Incident.class);

        return sources.buildMetadata();
    }

    public static void createTables()
    {
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.configure("createSchema.hibernate.cfg.xml");

            Metadata metadata = getMetadata(builder);

            new SchemaExport() //
                .setOutputFile(DB_SCHEMA_DDL) //
                .create(EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT), metadata);

            metadata.buildSessionFactory().close();
            System.out.println("Ok. You can view  DB Schema in file: " + DB_SCHEMA_DDL);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        /*
        String pwd = "superFunt";

        System.out.println("Authentication.encodeSHA256(" + pwd + ") = " + Authentication.encodeSHA256(pwd));
        String[] passwords = new String[] {"admin", "actuary", "coordinator", "user"};

        for (String password : passwords) {
            System.out.println("Authentication.encodeSHA256(" + password + ") = " + Authentication.encodeSHA256(password));
        } */
        createTables();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
