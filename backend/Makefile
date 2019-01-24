# Makefile.

SONAR_HOST:=https://sonarcloud.io
SONAR_TOKEN:=0286bbae12fc5eab6c5c82470b9c7b57cb4723c2
SONAR_PROJECT_KEY:=pedroarapua_customers
SONAR_ORGANIZATION:=pedroarapua-github

tests:
	./mvnw clean test jacoco:report

coveralls: tests
	./mvnw coveralls:report \
		-DrepoToken=$(COVERALLS_TOKEN)

sonar:
	./mvnw sonar:sonar -Dsonar.projectKey=$(SONAR_PROJECT_KEY) \
  		-Dsonar.organization=$(SONAR_ORGANIZATION) \
		-Dsonar.host.url=$(SONAR_HOST) \
  		-Dsonar.login=$(SONAR_TOKEN)
