github-recommender
==================

Berlin Geekettes Hackathon project to recommend interesting open source projects to GitHub users based on their interests. This works, but right now only on a limited (random) selection of a few tousand repos. Please keep in mind this is a hack, so the code is neither particularly robust, clean or pretty. ;)

A live demo is here: http://github-recommender.herokuapp.com/

Written by Lina, Julia & Ellen (@ellen_koenig)

Hints
---------

As we talk to the github api a lot, we need you get a personal authentification key:

    $ curl -u 'githubuser' -d '{"note":"github repo recommender"}' https://api.github.com/authorizations

The output should include a line:

    "token": "yourtokenhash"

Put it in a environment variable:

    export GITOAUTHTOKEN=yourtokenhash
