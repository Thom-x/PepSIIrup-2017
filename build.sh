#!/usr/bin/env bash
dotnet restore && dotnet build **/project.json && dotnet install NUnit.Runners -Version 2.6.4 -OutputDirectory testrunner