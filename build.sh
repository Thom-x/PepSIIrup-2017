#!/usr/bin/env bash
MSBuild.SonarQube.Runner.exe begin /k:"org.sonarqube:sonarqube-scanner-msbuild" /n:"TraineeSIIp" /v:"1.0"
dotnet restore && dotnet build **/project.json && dotnet test Micro-Services/CsEventService/src/TestLibrary/project.json
MSBuild.SonarQube.Runner.exe end