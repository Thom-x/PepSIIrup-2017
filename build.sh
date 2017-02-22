#!/usr/bin/env bash
"C:\SonarQube\bin\SonarQube.Scanner.MSBuild.exe" begin /k:"org.sonarqube:sonarqube-scanner-msbuild" /n:"TraineeSIIp" /v:"1.0"
dotnet restore && dotnet build **/project.json && dotnet test Micro-Services/CsEventService/src/TestLibrary/project.json
"C:\SonarQube\bin\SonarQube.Scanner.MSBuild.exe" end