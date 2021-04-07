///*
// * Copyright (C) 2018 Mani Moayedi (acidmanic.moayedi@gmail.com)
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package com.acidmanic.release.commands;
//
//import com.acidmanic.release.logging.Logger;
//import release.Application;
//
///**
// *
// * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
// */
//@Deprecated
//public class Manual extends ReleaseCommandBase {
//
//    private com.acidmanic.release.versions.Version version;
//
//    @Override
//    protected String getUsageString() {
//        return "Will set given version into all present Versionable-Systems, "
//                + "then tries to perform a release. \n"
//                + "Optional parameter: "
//                + RELEASE_TYPE_DESCRIPTION ;
//    }
//
//    @Override
//    protected String argumentsDesciption() {
//        return "<version-string> [" + RELEASE_TYPE_ARG_DEC + "]";
//    }
//
//    @Override
//    public void execute() {
//        if (!verifyArguments()) {
//            return;
//        }
//
//        Logger.log("Version: " + this.version.getVersionString(), this);
//
//        performRelease(this.version);
//    }
//
//    private boolean verifyArguments() {
//        if (noArguments(1)) {
//            return false;
//        }
//        this.version = Application.getVersionFactory().blank();
//        return this.version.tryParse(args[0]);
//    }
//
//}
