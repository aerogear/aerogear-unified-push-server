# AeroGear UnifiedPush Server [![Build Status](https://travis-ci.org/aerogear/aerogear-unifiedpush-server.png)](https://travis-ci.org/aerogear/aerogear-unifiedpush-server)


## Getting started

Only three steps are needed to get going!

* Download the UnifiedPush Server ``WAR file`` from [here](http://aerogear.org/push/) and copy into ``$JBOSS/standalone/deployments``
* Copy this [datasource XML file](https://github.com/aerogear/aerogear-unifiedpush-server/blob/0.10.x/databases/unifiedpush-h2-ds.xml) into ``$JBOSS/standalone/deployments`` as well
* Start the Server (e.g. ``$JBOSS/bin/standalone.sh -b 0.0.0.0``)

Now go to ``http://localhost:8080/NAME_OF_THE_WAR_FILE`` and enjoy the UnifiedPush Server.
__NOTE:__ the default user/password is ```admin```:```123```


For more details about the current release, please consult the README on our [stable branch](https://github.com/aerogear/aerogear-unifiedpush-server/tree/0.10.x).

## Note on our Master branch

The master branch is currenly being worked on for a tight integration with the [Keycloak project](keycloak.org). To run the master branch it requires some initial setup steps for Keycloak. Eventually there will be a simple and tight integration.

### Some hints

After building the project (and keycloak's master branch), deploy the ``auth-server.war`` file; once that is done, deploy the ``ag-push.war`` file. Login with admin:admin (you will be asked to update the password)...

### Instructions for Keycloak administration console

Note: The instructions below are pretty much based on [Keycloak integration with UPS](https://github.com/keycloak/keycloak/blob/master/project-integrations/aerogear-ups/README.md).

* The aerogear security admin (keycloak) http://localhost:8080/auth/admin/aerogear/console/index.html
* The aerogear user account page (keycloak) http://localhost:8080/auth/realms/aerogear/account

## However
Because of this work, we highly recommend using the [stable branch](https://github.com/aerogear/aerogear-unifiedpush-server/tree/0.10.x), as discussed above!


## Developing and releasing UI

The sources for administration console UI are placed under `admin-ui`.

For a build of the `admin-ui` during release, you can just run a Maven build, the `admin-ui` will be compiled by `frontend-maven-plugin` during `server` module build.

For instructions how to develop `admin-ui`, refer to [`admin-ui/README.md`](https://github.com/aerogear/aerogear-unifiedpush-server/blob/master/admin-ui/README.md).


### Any questions ?

Join our [mailing list](https://lists.jboss.org/mailman/listinfo/aerogear-dev) for any questions and help! We really hope you enjoy our UnifiedPush Server!
