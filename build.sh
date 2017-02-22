#!/usr/bin/env bash
MSBuild.SonarQube.Runner.exe begin /key:"org.sonarqube:sonarqube-scanner-msbuild" /name:"TraineeSIIp" /version:"1.0"
dotnet restore && dotnet build **/project.json && dotnet test Micro-Services/CsEventService/src/TestLibrary/project.json
MSBuild.SonarQube.Runner.exe end