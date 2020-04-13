![Releasy](icon/icon.png)

Releasy!
========


Releasy, is a simple command line application for performing software releases, 
easier and managed. It manipulates version string in your source code, and sets this 
version on available version control.

Releasy can detect/modify versions on:


*   Cocoapods
*   Java Manifest
*   Maven Pom
*   NodeJs Package
*   NuGet (Spec File)
*   NuGet (.Net Core Project File)
*   XCode Project

Currently Implemented Version controls :

*   Git

Currently supported version systems:

*   Semantic Versioning (Major.Feature.Fixes[-postfix])



How To Use
==========


Releasy have two straight forward commands: __auto__ and __manual__.

Auto
----

Detects latest version, increments this version and sets the new version in your 
version control and updates your source files. You can modify how it increments 
the version by using arguments:

*   feat: Meaning you added some __feature__s.
*   fix: Meaning you had some bugs __fix__ed
*   des: Meaning you had changes in your __Design__.

you can also follow these arguments with a release type term like rc, nightly, 
dev, etc.

Manual
------

With this command you directly set the version in your version control and your source files.


Bonus!
======

If you have a Readme file written in markdown (md), and you provided a code snippet 
that gets your library from a package manager, Releasy might detect the version on it 
and update that too. it currently can detect code for using these package manager:

*   Carthage
*   Cocoapods
*   Gradle
*   Maven



Next To Do:
===========

These are the updates which hopefully I perform in newer versions.

*   Replace GitWrapper with jGit library.
*   Seperate source control updating stage from release on version control systems.
*   Add support for SVN.
*   Add Readme Updater for NuGet.
*   Move VisualStudio updating from Readme updaters to source versionables.





