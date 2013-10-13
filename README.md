github-recommender
==================

Berlin Geekettes Hackathon project to recommend interesting open source projects to GitHub users based on their interests

Hints
---------

As we talk to the github api a lot, we need you get a personal authentification key:

    $ curl -u 'githubuser' -d '{"note":"github repo recommender"}' https://api.github.com/authorizations

The output should include a line:

    "token": "yourtokenhash"

Put it in a environment variable:

    export GITOAUTHTOKEN=yourtokenhash
