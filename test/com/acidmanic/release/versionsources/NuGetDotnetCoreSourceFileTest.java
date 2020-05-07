/*
 * Copyright (C) 2020 Acidmanic
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;
import java.io.File;

/**
 *
 * @author Acidmanic
 */
public class NuGetDotnetCoreSourceFileTest extends SourceVersionFileTestBase {

    private final String content = "<Project Sdk=\"Microsoft.NET.Sdk\">\n"
            + "\n"
            + "  <PropertyGroup>\n"
            + "    <TargetFramework>netcoreapp2.2</TargetFramework>\n"
            + "    <AspNetCoreHostingModel>InProcess</AspNetCoreHostingModel>\n"
            + "    <RootNamespace></RootNamespace>\n"
            + "\n"
            + "\n"
            + "    <!-- NuGet -->\n"
            + "    <Id>EnTier</Id>\n"
            + "    <PackageId>EnTier</PackageId>\n"
            + "    <Version>{VERSION_HERE}</Version>\n"
            + "    <Authors>Mani Moayedi</Authors>\n"
            + "    <Company>Acidmanic</Company>\n"
            + "    <PackageIcon>graphics\\EnTier.png</PackageIcon>\n"
            + "    <Description>Easy NTier Architecture, Working out of the box!(Kind of!!)</Description>\n"
            + "    <Summary>Easy NTier Architecture, Working out of the box!(Kind of!!)</Summary>\n"
            + "   \n"
            + "  </PropertyGroup>"
            + "</Project>";
    
    private final File versionFile = new File("nuget_docnet_core.csproj");

    @Override
    protected File makeSourceFile(String versionString) {
                
        String content = this.content.replace("{VERSION_HERE}", versionString);
        
        new FileIOHelper().tryWriteAll(versionFile, content);
        
        return versionFile;
    }

    @Override
    protected VersionSourceFile createNew() {
        return new NuGetDotnetCore();
    }

}
