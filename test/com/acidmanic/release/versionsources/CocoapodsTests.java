/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versionsources;

import com.acidmanic.io.file.FileIOHelper;

/**
 *
 * @author diego
 */
public class CocoapodsTests extends VersionSourceFileTest<Cocoapods> {

    @Override
    protected Cocoapods createInstance() {
        return new Cocoapods();
    }
    
    private final String specFileName = "fakeproject.podspec";

    @Override
    protected void makePresentInTestEnvironment(String version, String... args) {
        String content = "Pod::Spec.new do |s|\n"
                + "  s.name             = 'TestProject'\n"
                + "  s.version          = '" + version + "'\n"
                + "  s.summary          = 'This is a lie!.'\n"
                + "  s.swift_version    = '4.0'\n"
                + "\n"
                + "  s.description      = <<-DESC\n"
                + "This is a lie, Its not a project.\n"
                + "  s.homepage         =  'https://github.com/Acidmanic'\n"
                + "  s.license          = { :type => 'MIT', :file => 'LICENSE' }\n"
                + "  s.author           = { 'Acidmanic' => 'acidmanic.moayedi@gmail.com' }\n"
                + "  s.source           = { :git => 'https://github.com/Acidmanic', :tag => s.version }\n"
                + "  s.social_media_url = 'https://about.me/moayedi'\n"
                + "\n"
                + "  s.ios.deployment_target = '9.3'\n"
                + "  s.osx.deployment_target = '10.12'\n"
                + "  s.watchos.deployment_target = \"3.2\"\n"
                + "  s.tvos.deployment_target = '10.2'\n"
                + "\n"
                + "  s.source_files = 'FakeProject/Classes/**/*'\n"
                + "\n"
                + "end";
        
        new FileIOHelper().tryWriteAll(specFileName, content);
    }

    @Override
    protected void removeFromTestEnvironment() {
        deleteAnyFile("podspec");
    }

}
