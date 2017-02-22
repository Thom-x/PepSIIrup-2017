#!/usr/bin/env bash
<<<<<<< HEAD
MSBuild.SonarQube.Runner.exe begin /key:"org.sonarqube:sonarqube-scanner-msbuild" /name:"TraineeSIIp" /version:"1.0"
=======
SonarQube.Scanner.MSBuild.exe begin /key:"org.sonarqube:sonarqube-scanner-msbuild" /name:"TraineeSIIp" /version:"1.0"
>>>>>>> d8e66690bd9b8c8626a52e4ddfd1a02c23f2abed
dotnet restore && dotnet build **/project.json && dotnet test Micro-Services/CsEventService/src/TestLibrary/project.json
MSBuild.SonarQube.Runner.exe end