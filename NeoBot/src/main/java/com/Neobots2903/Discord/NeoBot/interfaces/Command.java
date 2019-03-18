package com.Neobots2903.Discord.NeoBot.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Command {
    public String Name();

    public String[] Aliases() default {};

    public String Summary() default "This command does not have a summary yet :/";

    public String Syntax() default "";

    public boolean SpecialPerms() default false;
}

