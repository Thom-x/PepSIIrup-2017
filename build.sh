#!/usr/bin/env bash
SonarQube.Scanner.MSBuild.exe begin /key:"org.sonarqube:sonarqube-scanner-msbuild" /name:"TraineeSIIp" /version:"1.0"
dotnet restore && dotnet build **/project.json && dotnet test Micro-Services/CsEventService/src/TestLibrary/project.json
SonarQube.Scanner.MSBuild.exe end