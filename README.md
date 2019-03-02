
# ucsb-cs56-goodsleepalarm
gives users sleep suggestions based on schedule...

=======

https://cs56-goodsleepalarm.herokuapp.com/


Before YOU START - GIT CLONE THIS REPO!!!!

Step1 : Create a Heroku Account
If you do not already have a Heroku account, navigate to https://www.heroku.com/ and click the “Sign up for Free” link.

Heroku Signup

You’ll be asked for:

First Name
Last Name
Email (you may use any email address you like)
Company (you may leave this blank).

Logged into CSIL (or one of the machines in the CSTL, i.e. Phelps 3525), use this command to login to Heroku at the command line:

`heroku login`
Then, use this command to create a new web app running on heroku. Substitute your github id in place of githubid. Note that you should convert your githubid to all lowercase; heroku web-app names do not permit uppercase letters.

`heroku create "your project name"`

Then, Modify the pom.xml file to refer to your heroku app
In the pom.xml file, locate this section. It is a plugin element, and should be located inside the plugins element.
Edit this line with your app name instead of this one:  `<appName>webapp-name-here</appName>`



Step 2) Setting up Google Credentials

1) Open the Google API Console Credentials page : https://console.developers.google.com/apis/dashboard

2) From the project drop-down, choose Create a new project, enter a name for the project, and optionally, edit the provided      project ID. Click Create.

3) On the Credentials page, select Create credentials, then select OAuth client ID.

4) You may be prompted to set a product name on the Consent screen; if so, click Configure consent screen, supply the requested information including adding webapp-name-here.herokuapp.com to the authorized domains, and click Save to return to the Credentials screen.

5) Select Web application for the Application type, enter the application name. Then in the redirect urls, add all of the following: http://localhost:8080/login	
  https://webapp-name-here.herokuapp.com	
  https://webapp-name-here.herokuapp.com/login	
  http://webapp-name-here.herokuapp.com/login	
  http://webapp-name-here.herokuapp.com.
 Then click create.

6) On the page that appears, click on your project, then copy the client ID and client secret to your clipboard, as you will need them when you configure your client library.

Step 3) Using Google Credentials
1. To use your Google credentials for localhost / heroku, copy localhost / heroku.json.SAMPLE to localhost / heroku.json at the project root using the commands: `cp localhost.json.SAMPLE localhost.json` and `cp heroku.json.SAMPLE heroku.json`
2. Modify localhost / heroku.json by putting in your client id and client secret where indicated
3. For heroku, run `./setHerokuEnv.py --app webapp-name-here` ; For localhost run . env.sh from the root
4. Finally deploy / run the webapp with `mvn spring-boot:run` or `mvn package heroku:deploy`
