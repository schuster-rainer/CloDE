# CloDE

## Introduction

This is a very early alpha version I created some month ago and since then never touched. Basically not munch of an architecture. I'm getting used to clojure ... My goald is to write all code in clojure to be easily modified atm runtime from the system REPL. Perhaps with some sort of custom *.cs project compilation, either by using Roslyn or by just calling into csc.exe or MONOs mcs.exe. Atm. there is some pain related to Generics. As I'm getting more used to Clojure-CLR. I'm replacing the code from Clode.Extensions with clojure code. There are no graphical commands in the GUI right now. Only some very few from the REPL (no file saving atm.)

## Basic Concept

CloDE loads its contents (views, controls, themes) from xaml dynamically (with all related quirks). Its dived into modules with an init function being called from clode.clj. So it's a monolythic approach right now until I have the basic features working as I want them to be.

## Features

Almost all features are somewhat quirky, mostly proof of concept and have to be refactored

- Theming
- Code editor (AvalonEdit)
- A very hackish like System-REPL (AvalonEdit based, namespacing issues). Can show images from the image subfolder. The pattern #img "filename" gets replaced by avalonedit.
- Command History up-down (STRG-UP, STRG-DOWN) (a minor bug in there at the corner cases)
- Property Grid

## Dependencies

- Expression Blend 4 SDK (http://www.microsoft.com/en-US/download/details.aspx?id=10801)
- Clode.Extensions
- nuget and packages listed in dependencies.clj

I'm running a x64 Win7 so you may have to adjust the path from boot.clj to meet your 
installations

If you wanna play around with the monocecil bindings you have to download and extract ILSpy into dependencies/**package-path**/ILSpy and uncomment the according references from boot.clj

nleiningen basically works, but I decided to stick to the dependencies.clj right now. I'm planning to extend and integrate nleiningen. 

### Preparation 

1. Installing the Expression SDK 
2. Compile Clode.Extensions
3. Clojure.Main.exe -m dependencies

## Running

Clojure.Main.exe -m clode Shell_Complete.xaml
CloDE will pull down

I have to register the dynamically loaded xaml controls in order for wpf/find-elem to work, or rewrite it to use another approach. Then I can use a modular approach. Maybe should have a look at some MVVM framework.

## Goals

### General

- Have fun!
- Have fun!
- Have fun!
- Create the best Shell Environment written in ClojureCLR

### Short Term

- Enhanced fully functional REPL with nREPL support
- Implement Logging
- Basic Editor Features to use CloDE for developing CloDE
- Figuring out an extension architecture to easily plug into
- nLeiningen/nuget dependecy management (project.clj)
- Split functionality into packages and make them available from nuget
- Module Browser
- TestRunner

### Long Term

- Live Coding Environment (inline results from eval)
- Adopt common IDE features from vsClojure and other Clojure IDEs (Debugger, Code Completion, Code Navigator)
- Alternative Readers Sources (Database, ZIP, Dropbox ...)
- Create a hosting API for other services (Roslyn, F#, Ironscheme, IronPython, PowerShell, ...)
- Make it CrossPlattform
- Create UI Bindings for running on other OSes (MacOS and Linux)
- (integrate into Xamarin.Studio, SharpDevelop)

### Some other Ideas

- All sorts of WYSIWYG text markup/documentation editors like Markdown, AsciiDoc or DocBook
