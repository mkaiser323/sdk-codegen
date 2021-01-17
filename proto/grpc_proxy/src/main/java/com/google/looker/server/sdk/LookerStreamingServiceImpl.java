// MIT License
//
// Copyright (c) 2019 Looker Data Sciences, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

// 401 API methods


package com.google.looker.server.sdk;

import com.google.looker.proto.services.*;
import com.google.looker.proto.services.LookerStreamingServiceGrpc.LookerStreamingServiceImplBase;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class LookerStreamingServiceImpl extends LookerStreamingServiceImplBase {
    
  //#region ApiAuth: API Authentication

  /**   * ### Present client credentials to obtain an authorization token
   * 
   * Looker API implements the OAuth2 [Resource Owner Password Credentials Grant](https://looker.com/docs/r/api/outh2_resource_owner_pc) pattern.
   * The client credentials required for this login must be obtained by creating an API3 key on a user account
   * in the Looker Admin console. The API3 key consists of a public `client_id` and a private `client_secret`.
   * 
   * The access token returned by `login` must be used in the HTTP Authorization header of subsequent
   * API requests, like this:
   * ```
   * Authorization: token 4QDkCyCtZzYgj4C2p2cj3csJH7zqS5RzKs2kTnG4
   * ```
   * Replace "4QDkCy..." with the `access_token` value returned by `login`.
   * The word `token` is a string literal and must be included exactly as shown.
   * 
   * This function can accept `client_id` and `client_secret` parameters as URL query params or as www-form-urlencoded params in the body of the HTTP request. Since there is a small risk that URL parameters may be visible to intermediate nodes on the network route (proxies, routers, etc), passing credentials in the body of the request is considered more secure than URL params.
   * 
   * Example of passing credentials in the HTTP request body:
   * ````
   * POST HTTP /login
   * Content-Type: application/x-www-form-urlencoded
   * 
   * client_id=CGc9B7v7J48dQSJvxxx&client_secret=nNVS9cSS3xNpSC9JdsBvvvvv
   * ````
   * 
   * ### Best Practice:
   * Always pass credentials in body params. Pass credentials in URL query params **only** when you cannot pass body params due to application, tool, or other limitations.
   * 
   * For more information and detailed examples of Looker API authorization, see [How to Authenticate to Looker API3](https://github.com/looker/looker-sdk-ruby/blob/master/authentication.md).
   * 
   */
  public void Login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create an access token that runs as a given user.
   * 
   * This can only be called by an authenticated admin user. It allows that admin to generate a new
   * authentication token for the user with the given user id. That token can then be used for subsequent
   * API calls - which are then performed *as* that target user.
   * 
   * The target user does *not* need to have a pre-existing API client_id/client_secret pair. And, no such
   * credentials are created by this call.
   * 
   * This allows for building systems where api user authentication for an arbitrary number of users is done
   * outside of Looker and funneled through a single 'service account' with admin permissions. Note that a
   * new access token is generated on each call. If target users are going to be making numerous API
   * calls in a short period then it is wise to cache this authentication token rather than call this before
   * each of those API calls.
   * 
   * See 'login' for more detail on the access token and how to use it.
   * 
   */
  public void LoginUser(LoginUserRequest request, StreamObserver<LoginUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Logout of the API and invalidate the current access token.
   * 
   */
  public void Logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion ApiAuth: API Authentication

  //#region Auth: Manage User Authentication Configuration

  /**   * ### Create SSO Embed URL
   * 
   * Creates an SSO embed URL and cryptographically signs it with an embed secret.
   * This signed URL can then be used to instantiate a Looker embed session in a PBL web application.
   * Do not make any modifications to this URL - any change may invalidate the signature and
   * cause the URL to fail to load a Looker embed session.
   * 
   * A signed SSO embed URL can only be used once. After it has been used to request a page from the
   * Looker server, the URL is invalid. Future requests using the same URL will fail. This is to prevent
   * 'replay attacks'.
   * 
   * The `target_url` property must be a complete URL of a Looker UI page - scheme, hostname, path and query params.
   * To load a dashboard with id 56 and with a filter of `Date=1 years`, the looker URL would look like `https:/myname.looker.com/dashboards/56?Date=1%20years`.
   * The best way to obtain this target_url is to navigate to the desired Looker page in your web browser,
   * copy the URL shown in the browser address bar and paste it into the `target_url` property as a quoted string value in this API request.
   * 
   * Permissions for the embed user are defined by the groups in which the embed user is a member (group_ids property)
   * and the lists of models and permissions assigned to the embed user.
   * At a minimum, you must provide values for either the group_ids property, or both the models and permissions properties.
   * These properties are additive; an embed user can be a member of certain groups AND be granted access to models and permissions.
   * 
   * The embed user's access is the union of permissions granted by the group_ids, models, and permissions properties.
   * 
   * This function does not strictly require all group_ids, user attribute names, or model names to exist at the moment the
   * SSO embed url is created. Unknown group_id, user attribute names or model names will be passed through to the output URL.
   * To diagnose potential problems with an SSO embed URL, you can copy the signed URL into the Embed URI Validator text box in `<your looker instance>/admin/embed`.
   * 
   * The `secret_id` parameter is optional. If specified, its value must be the id of an active secret defined in the Looker instance.
   * if not specified, the URL will be signed using the newest active secret defined in the Looker instance.
   * 
   * #### Security Note
   * Protect this signed URL as you would an access token or password credentials - do not write
   * it to disk, do not pass it to a third party, and only pass it through a secure HTTPS
   * encrypted transport.
   * 
   */
  public void CreateSsoEmbedUrl(CreateSsoEmbedUrlRequest request, StreamObserver<CreateSsoEmbedUrlResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create an Embed URL
   * 
   * Creates an embed URL that runs as the Looker user making this API call. ("Embed as me")
   * This embed URL can then be used to instantiate a Looker embed session in a
   * "Powered by Looker" (PBL) web application.
   * 
   * This is similar to Private Embedding (https://docs.looker.com/r/admin/embed/private-embed). Instead of
   * of logging into the Web UI to authenticate, the user has already authenticated against the API to be able to
   * make this call. However, unlike Private Embed where the user has access to any other part of the Looker UI,
   * the embed web session created by requesting the EmbedUrlResponse.url in a browser only has access to
   * content visible under the `/embed` context.
   * 
   * An embed URL can only be used once, and must be used within 5 minutes of being created. After it
   * has been used to request a page from the Looker server, the URL is invalid. Future requests using
   * the same URL will fail. This is to prevent 'replay attacks'.
   * 
   * The `target_url` property must be a complete URL of a Looker Embedded UI page - scheme, hostname, path starting with "/embed" and query params.
   * To load a dashboard with id 56 and with a filter of `Date=1 years`, the looker Embed URL would look like `https://myname.looker.com/embed/dashboards/56?Date=1%20years`.
   * The best way to obtain this target_url is to navigate to the desired Looker page in your web browser,
   * copy the URL shown in the browser address bar, insert "/embed" after the host/port, and paste it into the `target_url` property as a quoted string value in this API request.
   * 
   * #### Security Note
   * Protect this embed URL as you would an access token or password credentials - do not write
   * it to disk, do not pass it to a third party, and only pass it through a secure HTTPS
   * encrypted transport.
   * 
   */
  public void CreateEmbedUrlAsMe(CreateEmbedUrlAsMeRequest request, StreamObserver<CreateEmbedUrlAsMeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the LDAP configuration.
   * 
   * Looker can be optionally configured to authenticate users against an Active Directory or other LDAP directory server.
   * LDAP setup requires coordination with an administrator of that directory server.
   * 
   * Only Looker administrators can read and update the LDAP configuration.
   * 
   * Configuring LDAP impacts authentication for all users. This configuration should be done carefully.
   * 
   * Looker maintains a single LDAP configuration. It can be read and updated.       Updates only succeed if the new state will be valid (in the sense that all required fields are populated);       it is up to you to ensure that the configuration is appropriate and correct).
   * 
   * LDAP is enabled or disabled for Looker using the **enabled** field.
   * 
   * Looker will never return an **auth_password** field. That value can be set, but never retrieved.
   * 
   * See the [Looker LDAP docs](https://www.looker.com/docs/r/api/ldap_setup) for additional information.
   * 
   */
  public void LdapConfig(LdapConfigRequest request, StreamObserver<LdapConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the LDAP configuration.
   * 
   * Configuring LDAP impacts authentication for all users. This configuration should be done carefully.
   * 
   * Only Looker administrators can read and update the LDAP configuration.
   * 
   * LDAP is enabled or disabled for Looker using the **enabled** field.
   * 
   * It is **highly** recommended that any LDAP setting changes be tested using the APIs below before being set globally.
   * 
   * See the [Looker LDAP docs](https://www.looker.com/docs/r/api/ldap_setup) for additional information.
   * 
   */
  public void UpdateLdapConfig(UpdateLdapConfigRequest request, StreamObserver<UpdateLdapConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the connection settings for an LDAP configuration.
   * 
   * This tests that the connection is possible given a connection_host and connection_port.
   * 
   * **connection_host** and **connection_port** are required. **connection_tls** is optional.
   * 
   * Example:
   * ```json
   * {
   *   "connection_host": "ldap.example.com",
   *   "connection_port": "636",
   *   "connection_tls": true
   * }
   * ```
   * 
   * No authentication to the LDAP server is attempted.
   * 
   * The active LDAP settings are not modified.
   * 
   */
  public void TestLdapConfigConnection(TestLdapConfigConnectionRequest request, StreamObserver<TestLdapConfigConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the connection authentication settings for an LDAP configuration.
   * 
   * This tests that the connection is possible and that a 'server' account to be used by Looker can       authenticate to the LDAP server given connection and authentication information.
   * 
   * **connection_host**, **connection_port**, and **auth_username**, are required.       **connection_tls** and **auth_password** are optional.
   * 
   * Example:
   * ```json
   * {
   *   "connection_host": "ldap.example.com",
   *   "connection_port": "636",
   *   "connection_tls": true,
   *   "auth_username": "cn=looker,dc=example,dc=com",
   *   "auth_password": "secret"
   * }
   * ```
   * 
   * Looker will never return an **auth_password**. If this request omits the **auth_password** field, then       the **auth_password** value from the active config (if present) will be used for the test.
   * 
   * The active LDAP settings are not modified.
   * 
   * 
   */
  public void TestLdapConfigAuth(TestLdapConfigAuthRequest request, StreamObserver<TestLdapConfigAuthResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the user authentication settings for an LDAP configuration without authenticating the user.
   * 
   * This test will let you easily test the mapping for user properties and roles for any user without      needing to authenticate as that user.
   * 
   * This test accepts a full LDAP configuration along with a username and attempts to find the full info      for the user from the LDAP server without actually authenticating the user. So, user password is not      required.The configuration is validated before attempting to contact the server.
   * 
   * **test_ldap_user** is required.
   * 
   * The active LDAP settings are not modified.
   * 
   * 
   */
  public void TestLdapConfigUserInfo(TestLdapConfigUserInfoRequest request, StreamObserver<TestLdapConfigUserInfoResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the user authentication settings for an LDAP configuration.
   * 
   * This test accepts a full LDAP configuration along with a username/password pair and attempts to       authenticate the user with the LDAP server. The configuration is validated before attempting the       authentication.
   * 
   * Looker will never return an **auth_password**. If this request omits the **auth_password** field, then       the **auth_password** value from the active config (if present) will be used for the test.
   * 
   * **test_ldap_user** and **test_ldap_password** are required.
   * 
   * The active LDAP settings are not modified.
   * 
   * 
   */
  public void TestLdapConfigUserAuth(TestLdapConfigUserAuthRequest request, StreamObserver<TestLdapConfigUserAuthResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### List All OAuth Client Apps
   * 
   * Lists all applications registered to use OAuth2 login with this Looker instance, including
   * enabled and disabled apps.
   * 
   * Results are filtered to include only the apps that the caller (current user)
   * has permission to see.
   * 
   */
  public void AllOauthClientApps(AllOauthClientAppsRequest request, StreamObserver<AllOauthClientAppsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Oauth Client App
   * 
   * Returns the registered app client with matching client_guid.
   * 
   */
  public void OauthClientApp(OauthClientAppRequest request, StreamObserver<OauthClientAppResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Register an OAuth2 Client App
   * 
   * Registers details identifying an external web app or native app as an OAuth2 login client of the Looker instance.
   * The app registration must provide a unique client_guid and redirect_uri that the app will present
   * in OAuth login requests. If the client_guid and redirect_uri parameters in the login request do not match
   * the app details registered with the Looker instance, the request is assumed to be a forgery and is rejected.
   * 
   */
  public void RegisterOauthClientApp(RegisterOauthClientAppRequest request, StreamObserver<RegisterOauthClientAppResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update OAuth2 Client App Details
   * 
   * Modifies the details a previously registered OAuth2 login client app.
   * 
   */
  public void UpdateOauthClientApp(UpdateOauthClientAppRequest request, StreamObserver<UpdateOauthClientAppResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete OAuth Client App
   * 
   * Deletes the registration info of the app with the matching client_guid.
   * All active sessions and tokens issued for this app will immediately become invalid.
   * 
   * ### Note: this deletion cannot be undone.
   * 
   */
  public void DeleteOauthClientApp(DeleteOauthClientAppRequest request, StreamObserver<DeleteOauthClientAppResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Invalidate All Issued Tokens
   * 
   * Immediately invalidates all auth codes, sessions, access tokens and refresh tokens issued for
   * this app for ALL USERS of this app.
   * 
   */
  public void InvalidateTokens(InvalidateTokensRequest request, StreamObserver<InvalidateTokensResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Activate an app for a user
   * 
   * Activates a user for a given oauth client app. This indicates the user has been informed that
   * the app will have access to the user's looker data, and that the user has accepted and allowed
   * the app to use their Looker account.
   * 
   * Activating a user for an app that the user is already activated with returns a success response.
   * 
   */
  public void ActivateAppUser(ActivateAppUserRequest request, StreamObserver<ActivateAppUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Deactivate an app for a user
   * 
   * Deactivate a user for a given oauth client app. All tokens issued to the app for
   * this user will be invalid immediately. Before the user can use the app with their
   * Looker account, the user will have to read and accept an account use disclosure statement for the app.
   * 
   * Admin users can deactivate other users, but non-admin users can only deactivate themselves.
   * 
   * As with most REST DELETE operations, this endpoint does not return an error if the indicated
   * resource (app or user) does not exist or has already been deactivated.
   * 
   */
  public void DeactivateAppUser(DeactivateAppUserRequest request, StreamObserver<DeactivateAppUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the OIDC configuration.
   * 
   * Looker can be optionally configured to authenticate users against an OpenID Connect (OIDC)
   * authentication server. OIDC setup requires coordination with an administrator of that server.
   * 
   * Only Looker administrators can read and update the OIDC configuration.
   * 
   * Configuring OIDC impacts authentication for all users. This configuration should be done carefully.
   * 
   * Looker maintains a single OIDC configuation. It can be read and updated.       Updates only succeed if the new state will be valid (in the sense that all required fields are populated);       it is up to you to ensure that the configuration is appropriate and correct).
   * 
   * OIDC is enabled or disabled for Looker using the **enabled** field.
   * 
   */
  public void OidcConfig(OidcConfigRequest request, StreamObserver<OidcConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the OIDC configuration.
   * 
   * Configuring OIDC impacts authentication for all users. This configuration should be done carefully.
   * 
   * Only Looker administrators can read and update the OIDC configuration.
   * 
   * OIDC is enabled or disabled for Looker using the **enabled** field.
   * 
   * It is **highly** recommended that any OIDC setting changes be tested using the APIs below before being set globally.
   * 
   */
  public void UpdateOidcConfig(UpdateOidcConfigRequest request, StreamObserver<UpdateOidcConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a OIDC test configuration by test_slug.
   * 
   */
  public void OidcTestConfig(OidcTestConfigRequest request, StreamObserver<OidcTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a OIDC test configuration.
   * 
   */
  public void DeleteOidcTestConfig(DeleteOidcTestConfigRequest request, StreamObserver<DeleteOidcTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a OIDC test configuration.
   * 
   */
  public void CreateOidcTestConfig(CreateOidcTestConfigRequest request, StreamObserver<CreateOidcTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get password config.
   * 
   */
  public void PasswordConfig(PasswordConfigRequest request, StreamObserver<PasswordConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update password config.
   * 
   */
  public void UpdatePasswordConfig(UpdatePasswordConfigRequest request, StreamObserver<UpdatePasswordConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Force all credentials_email users to reset their login passwords upon their next login.
   * 
   */
  public void ForcePasswordResetAtNextLoginForAllUsers(ForcePasswordResetAtNextLoginForAllUsersRequest request, StreamObserver<ForcePasswordResetAtNextLoginForAllUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the SAML configuration.
   * 
   * Looker can be optionally configured to authenticate users against a SAML authentication server.
   * SAML setup requires coordination with an administrator of that server.
   * 
   * Only Looker administrators can read and update the SAML configuration.
   * 
   * Configuring SAML impacts authentication for all users. This configuration should be done carefully.
   * 
   * Looker maintains a single SAML configuation. It can be read and updated.       Updates only succeed if the new state will be valid (in the sense that all required fields are populated);       it is up to you to ensure that the configuration is appropriate and correct).
   * 
   * SAML is enabled or disabled for Looker using the **enabled** field.
   * 
   */
  public void SamlConfig(SamlConfigRequest request, StreamObserver<SamlConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the SAML configuration.
   * 
   * Configuring SAML impacts authentication for all users. This configuration should be done carefully.
   * 
   * Only Looker administrators can read and update the SAML configuration.
   * 
   * SAML is enabled or disabled for Looker using the **enabled** field.
   * 
   * It is **highly** recommended that any SAML setting changes be tested using the APIs below before being set globally.
   * 
   */
  public void UpdateSamlConfig(UpdateSamlConfigRequest request, StreamObserver<UpdateSamlConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a SAML test configuration by test_slug.
   * 
   */
  public void SamlTestConfig(SamlTestConfigRequest request, StreamObserver<SamlTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a SAML test configuration.
   * 
   */
  public void DeleteSamlTestConfig(DeleteSamlTestConfigRequest request, StreamObserver<DeleteSamlTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a SAML test configuration.
   * 
   */
  public void CreateSamlTestConfig(CreateSamlTestConfigRequest request, StreamObserver<CreateSamlTestConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Parse the given xml as a SAML IdP metadata document and return the result.
   * 
   */
  public void ParseSamlIdpMetadata(ParseSamlIdpMetadataRequest request, StreamObserver<ParseSamlIdpMetadataResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Fetch the given url and parse it as a SAML IdP metadata document and return the result.
   * Note that this requires that the url be public or at least at a location where the Looker instance
   * can fetch it without requiring any special authentication.
   * 
   */
  public void FetchAndParseSamlIdpMetadata(FetchAndParseSamlIdpMetadataRequest request, StreamObserver<FetchAndParseSamlIdpMetadataResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get session config.
   * 
   */
  public void SessionConfig(SessionConfigRequest request, StreamObserver<SessionConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update session config.
   * 
   */
  public void UpdateSessionConfig(UpdateSessionConfigRequest request, StreamObserver<UpdateSessionConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get currently locked-out users.
   * 
   */
  public void AllUserLoginLockouts(AllUserLoginLockoutsRequest request, StreamObserver<AllUserLoginLockoutsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search currently locked-out users.
   * 
   */
  public void SearchUserLoginLockouts(SearchUserLoginLockoutsRequest request, StreamObserver<SearchUserLoginLockoutsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Removes login lockout for the associated user.
   * 
   */
  public void DeleteUserLoginLockout(DeleteUserLoginLockoutRequest request, StreamObserver<DeleteUserLoginLockoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Auth: Manage User Authentication Configuration

  //#region Board: Manage Boards

  /**   * ### Get information about all boards.
   * 
   */
  public void AllBoards(AllBoardsRequest request, StreamObserver<AllBoardsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new board.
   * 
   */
  public void CreateBoard(CreateBoardRequest request, StreamObserver<CreateBoardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search Boards
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchBoards(SearchBoardsRequest request, StreamObserver<SearchBoardsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a board.
   * 
   */
  public void Board(BoardRequest request, StreamObserver<BoardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a board definition.
   * 
   */
  public void UpdateBoard(UpdateBoardRequest request, StreamObserver<UpdateBoardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a board.
   * 
   */
  public void DeleteBoard(DeleteBoardRequest request, StreamObserver<DeleteBoardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all board items.
   * 
   */
  public void AllBoardItems(AllBoardItemsRequest request, StreamObserver<AllBoardItemsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new board item.
   * 
   */
  public void CreateBoardItem(CreateBoardItemRequest request, StreamObserver<CreateBoardItemResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a board item.
   * 
   */
  public void BoardItem(BoardItemRequest request, StreamObserver<BoardItemResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a board item definition.
   * 
   */
  public void UpdateBoardItem(UpdateBoardItemRequest request, StreamObserver<UpdateBoardItemResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a board item.
   * 
   */
  public void DeleteBoardItem(DeleteBoardItemRequest request, StreamObserver<DeleteBoardItemResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all board sections.
   * 
   */
  public void AllBoardSections(AllBoardSectionsRequest request, StreamObserver<AllBoardSectionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new board section.
   * 
   */
  public void CreateBoardSection(CreateBoardSectionRequest request, StreamObserver<CreateBoardSectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a board section.
   * 
   */
  public void BoardSection(BoardSectionRequest request, StreamObserver<BoardSectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a board section definition.
   * 
   */
  public void UpdateBoardSection(UpdateBoardSectionRequest request, StreamObserver<UpdateBoardSectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a board section.
   * 
   */
  public void DeleteBoardSection(DeleteBoardSectionRequest request, StreamObserver<DeleteBoardSectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Board: Manage Boards

  //#region ColorCollection: Manage Color Collections

  /**   * ### Get an array of all existing Color Collections
   * Get a **single** color collection by id with [ColorCollection](#!/ColorCollection/color_collection)
   * 
   * Get all **standard** color collections with [ColorCollection](#!/ColorCollection/color_collections_standard)
   * 
   * Get all **custom** color collections with [ColorCollection](#!/ColorCollection/color_collections_custom)
   * 
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void AllColorCollections(AllColorCollectionsRequest request, StreamObserver<AllColorCollectionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a custom color collection with the specified information
   * 
   * Creates a new custom color collection object, returning the details, including the created id.
   * 
   * **Update** an existing color collection with [Update Color Collection](#!/ColorCollection/update_color_collection)
   * 
   * **Permanently delete** an existing custom color collection with [Delete Color Collection](#!/ColorCollection/delete_color_collection)
   * 
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void CreateColorCollection(CreateColorCollectionRequest request, StreamObserver<CreateColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get an array of all existing **Custom** Color Collections
   * Get a **single** color collection by id with [ColorCollection](#!/ColorCollection/color_collection)
   * 
   * Get all **standard** color collections with [ColorCollection](#!/ColorCollection/color_collections_standard)
   * 
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void ColorCollectionsCustom(ColorCollectionsCustomRequest request, StreamObserver<ColorCollectionsCustomResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get an array of all existing **Standard** Color Collections
   * Get a **single** color collection by id with [ColorCollection](#!/ColorCollection/color_collection)
   * 
   * Get all **custom** color collections with [ColorCollection](#!/ColorCollection/color_collections_custom)
   * 
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void ColorCollectionsStandard(ColorCollectionsStandardRequest request, StreamObserver<ColorCollectionsStandardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the default color collection
   * 
   * Use this to retrieve the default Color Collection.
   * 
   * Set the default color collection with [ColorCollection](#!/ColorCollection/set_default_color_collection)
   * 
   */
  public void DefaultColorCollection(DefaultColorCollectionRequest request, StreamObserver<DefaultColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set the global default Color Collection by ID
   * 
   * Returns the new specified default Color Collection object.
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void SetDefaultColorCollection(SetDefaultColorCollectionRequest request, StreamObserver<SetDefaultColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a Color Collection by ID
   * 
   * Use this to retrieve a specific Color Collection.
   * Get a **single** color collection by id with [ColorCollection](#!/ColorCollection/color_collection)
   * 
   * Get all **standard** color collections with [ColorCollection](#!/ColorCollection/color_collections_standard)
   * 
   * Get all **custom** color collections with [ColorCollection](#!/ColorCollection/color_collections_custom)
   * 
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void ColorCollection(ColorCollectionRequest request, StreamObserver<ColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a custom color collection by id.
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void UpdateColorCollection(UpdateColorCollectionRequest request, StreamObserver<UpdateColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a custom color collection by id
   * 
   * This operation permanently deletes the identified **Custom** color collection.
   * 
   * **Standard** color collections cannot be deleted
   * 
   * Because multiple color collections can have the same label, they must be deleted by ID, not name.
   * **Note**: Only an API user with the Admin role can call this endpoint. Unauthorized requests will return `Not Found` (404) errors.
   * 
   * 
   */
  public void DeleteColorCollection(DeleteColorCollectionRequest request, StreamObserver<DeleteColorCollectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion ColorCollection: Manage Color Collections

  //#region Command: Manage Commands

  /**   * ### Get All Commands.
   * 
   */
  public void GetAllCommands(GetAllCommandsRequest request, StreamObserver<GetAllCommandsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new command.
   * # Required fields: [:name, :linked_content_id, :linked_content_type]
   * # `linked_content_type` must be one of ["dashboard", "lookml_dashboard"]
   * #
   * 
   */
  public void CreateCommand(CreateCommandRequest request, StreamObserver<CreateCommandResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update an existing custom command.
   * # Optional fields: ['name', 'description']
   * #
   * 
   */
  public void UpdateCommand(UpdateCommandRequest request, StreamObserver<UpdateCommandResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete an existing custom command.
   * 
   */
  public void DeleteCommand(DeleteCommandRequest request, StreamObserver<DeleteCommandResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Command: Manage Commands

  //#region Config: Manage General Configuration

  /**   * Get the current Cloud Storage Configuration.
   * 
   */
  public void CloudStorageConfiguration(CloudStorageConfigurationRequest request, StreamObserver<CloudStorageConfigurationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Update the current Cloud Storage Configuration.
   * 
   */
  public void UpdateCloudStorageConfiguration(UpdateCloudStorageConfigurationRequest request, StreamObserver<UpdateCloudStorageConfigurationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the current status and content of custom welcome emails
   * 
   */
  public void CustomWelcomeEmail(CustomWelcomeEmailRequest request, StreamObserver<CustomWelcomeEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Update custom welcome email setting and values. Optionally send a test email with the new content to the currently logged in user.
   * 
   */
  public void UpdateCustomWelcomeEmail(UpdateCustomWelcomeEmailRequest request, StreamObserver<UpdateCustomWelcomeEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Requests to this endpoint will send a welcome email with the custom content provided in the body to the currently logged in user.
   * 
   */
  public void UpdateCustomWelcomeEmailTest(UpdateCustomWelcomeEmailTestRequest request, StreamObserver<UpdateCustomWelcomeEmailTestResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Retrieve the value for whether or not digest emails is enabled
   * 
   */
  public void DigestEmailsEnabled(DigestEmailsEnabledRequest request, StreamObserver<DigestEmailsEnabledResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the setting for enabling/disabling digest emails
   * 
   */
  public void UpdateDigestEmailsEnabled(UpdateDigestEmailsEnabledRequest request, StreamObserver<UpdateDigestEmailsEnabledResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Trigger the generation of digest email records and send them to Looker's internal system. This does not send
   * any actual emails, it generates records containing content which may be of interest for users who have become inactive.
   * Emails will be sent at a later time from Looker's internal system if the Digest Emails feature is enabled in settings.
   */
  public void CreateDigestEmailSend(CreateDigestEmailSendRequest request, StreamObserver<CreateDigestEmailSendResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set the menu item name and content for internal help resources
   * 
   */
  public void InternalHelpResourcesContent(InternalHelpResourcesContentRequest request, StreamObserver<InternalHelpResourcesContentResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Update internal help resources content
   * 
   */
  public void UpdateInternalHelpResourcesContent(UpdateInternalHelpResourcesContentRequest request, StreamObserver<UpdateInternalHelpResourcesContentResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get and set the options for internal help resources
   * 
   */
  public void InternalHelpResources(InternalHelpResourcesRequest request, StreamObserver<InternalHelpResourcesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Update internal help resources settings
   * 
   */
  public void UpdateInternalHelpResources(UpdateInternalHelpResourcesRequest request, StreamObserver<UpdateInternalHelpResourcesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get all legacy features.
   * 
   */
  public void AllLegacyFeatures(AllLegacyFeaturesRequest request, StreamObserver<AllLegacyFeaturesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the legacy feature with a specific id.
   * 
   */
  public void LegacyFeature(LegacyFeatureRequest request, StreamObserver<LegacyFeatureResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update information about the legacy feature with a specific id.
   * 
   */
  public void UpdateLegacyFeature(UpdateLegacyFeatureRequest request, StreamObserver<UpdateLegacyFeatureResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a list of locales that Looker supports.
   * 
   */
  public void AllLocales(AllLocalesRequest request, StreamObserver<AllLocalesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a list of timezones that Looker supports (e.g. useful for scheduling tasks).
   * 
   */
  public void AllTimezones(AllTimezonesRequest request, StreamObserver<AllTimezonesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all API versions supported by this Looker instance.
   * 
   */
  public void Versions(VersionsRequest request, StreamObserver<VersionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### This feature is enabled only by special license.
   * ### Gets the whitelabel configuration, which includes hiding documentation links, custom favicon uploading, etc.
   * 
   */
  public void WhitelabelConfiguration(WhitelabelConfigurationRequest request, StreamObserver<WhitelabelConfigurationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the whitelabel configuration
   * 
   */
  public void UpdateWhitelabelConfiguration(UpdateWhitelabelConfigurationRequest request, StreamObserver<UpdateWhitelabelConfigurationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Config: Manage General Configuration

  //#region Connection: Manage Database Connections

  /**   * ### Get information about all connections.
   * 
   */
  public void AllConnections(AllConnectionsRequest request, StreamObserver<AllConnectionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a connection using the specified configuration.
   * 
   */
  public void CreateConnection(CreateConnectionRequest request, StreamObserver<CreateConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a connection.
   * 
   */
  public void Connection(ConnectionRequest request, StreamObserver<ConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a connection using the specified configuration.
   * 
   */
  public void UpdateConnection(UpdateConnectionRequest request, StreamObserver<UpdateConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a connection.
   * 
   */
  public void DeleteConnection(DeleteConnectionRequest request, StreamObserver<DeleteConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a connection override.
   * 
   */
  public void DeleteConnectionOverride(DeleteConnectionOverrideRequest request, StreamObserver<DeleteConnectionOverrideResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test an existing connection.
   * 
   * Note that a connection's 'dialect' property has a 'connection_tests' property that lists the
   * specific types of tests that the connection supports.
   * 
   * This API is rate limited.
   * 
   * Unsupported tests in the request will be ignored.
   * 
   */
  public void TestConnection(TestConnectionRequest request, StreamObserver<TestConnectionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test a connection configuration.
   * 
   * Note that a connection's 'dialect' property has a 'connection_tests' property that lists the
   * specific types of tests that the connection supports.
   * 
   * This API is rate limited.
   * 
   * Unsupported tests in the request will be ignored.
   * 
   */
  public void TestConnectionConfig(TestConnectionConfigRequest request, StreamObserver<TestConnectionConfigResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all dialects.
   * 
   */
  public void AllDialectInfos(AllDialectInfosRequest request, StreamObserver<AllDialectInfosResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get all External OAuth Applications.
   * 
   */
  public void AllExternalOauthApplications(AllExternalOauthApplicationsRequest request, StreamObserver<AllExternalOauthApplicationsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create an OAuth Application using the specified configuration.
   * 
   */
  public void CreateExternalOauthApplication(CreateExternalOauthApplicationRequest request, StreamObserver<CreateExternalOauthApplicationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all SSH Servers.
   * 
   */
  public void AllSshServers(AllSshServersRequest request, StreamObserver<AllSshServersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create an SSH Server.
   * 
   */
  public void CreateSshServer(CreateSshServerRequest request, StreamObserver<CreateSshServerResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about an SSH Server.
   * 
   */
  public void SshServer(SshServerRequest request, StreamObserver<SshServerResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update an SSH Server.
   * 
   */
  public void UpdateSshServer(UpdateSshServerRequest request, StreamObserver<UpdateSshServerResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete an SSH Server.
   * 
   */
  public void DeleteSshServer(DeleteSshServerRequest request, StreamObserver<DeleteSshServerResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the SSH Server
   * 
   */
  public void TestSshServer(TestSshServerRequest request, StreamObserver<TestSshServerResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all SSH Tunnels.
   * 
   */
  public void AllSshTunnels(AllSshTunnelsRequest request, StreamObserver<AllSshTunnelsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create an SSH Tunnel
   * 
   */
  public void CreateSshTunnel(CreateSshTunnelRequest request, StreamObserver<CreateSshTunnelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about an SSH Tunnel.
   * 
   */
  public void SshTunnel(SshTunnelRequest request, StreamObserver<SshTunnelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update an SSH Tunnel
   * 
   */
  public void UpdateSshTunnel(UpdateSshTunnelRequest request, StreamObserver<UpdateSshTunnelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete an SSH Tunnel
   * 
   */
  public void DeleteSshTunnel(DeleteSshTunnelRequest request, StreamObserver<DeleteSshTunnelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Test the SSH Tunnel
   * 
   */
  public void TestSshTunnel(TestSshTunnelRequest request, StreamObserver<TestSshTunnelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the SSH public key
   * 
   * Get the public key created for this instance to identify itself to a remote SSH server.
   * 
   */
  public void SshPublicKey(SshPublicKeyRequest request, StreamObserver<SshPublicKeyResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Connection: Manage Database Connections

  //#region Content: Manage Content

  /**   * ### Search Favorite Content
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchContentFavorites(SearchContentFavoritesRequest request, StreamObserver<SearchContentFavoritesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get favorite content by its id
   */
  public void ContentFavorite(ContentFavoriteRequest request, StreamObserver<ContentFavoriteResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete favorite content
   */
  public void DeleteContentFavorite(DeleteContentFavoriteRequest request, StreamObserver<DeleteContentFavoriteResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create favorite content
   */
  public void CreateContentFavorite(CreateContentFavoriteRequest request, StreamObserver<CreateContentFavoriteResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all content metadata in a space.
   * 
   */
  public void AllContentMetadatas(AllContentMetadatasRequest request, StreamObserver<AllContentMetadatasResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about an individual content metadata record.
   * 
   */
  public void ContentMetadata(ContentMetadataRequest request, StreamObserver<ContentMetadataResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Move a piece of content.
   * 
   */
  public void UpdateContentMetadata(UpdateContentMetadataRequest request, StreamObserver<UpdateContentMetadataResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### All content metadata access records for a content metadata item.
   * 
   */
  public void AllContentMetadataAccesses(AllContentMetadataAccessesRequest request, StreamObserver<AllContentMetadataAccessesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create content metadata access.
   * 
   */
  public void CreateContentMetadataAccess(CreateContentMetadataAccessRequest request, StreamObserver<CreateContentMetadataAccessResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update type of access for content metadata.
   * 
   */
  public void UpdateContentMetadataAccess(UpdateContentMetadataAccessRequest request, StreamObserver<UpdateContentMetadataAccessResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Remove content metadata access.
   * 
   */
  public void DeleteContentMetadataAccess(DeleteContentMetadataAccessRequest request, StreamObserver<DeleteContentMetadataAccessResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get an image representing the contents of a dashboard or look.
   * 
   * The returned thumbnail is an abstract representation of the contents of a dashbord or look and does not
   * reflect the actual data displayed in the respective visualizations.
   * 
   */
  public void ContentThumbnail(ContentThumbnailRequest request, StreamObserver<ContentThumbnailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Validate All Content
   * 
   * Performs validation of all looks and dashboards
   * Returns a list of errors found as well as metadata about the content validation run.
   * 
   */
  public void ContentValidation(ContentValidationRequest request, StreamObserver<ContentValidationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search Content Views
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchContentViews(SearchContentViewsRequest request, StreamObserver<SearchContentViewsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a vector image representing the contents of a dashboard or look.
   * 
   * # DEPRECATED:  Use [content_thumbnail()](#!/Content/content_thumbnail)
   * 
   * The returned thumbnail is an abstract representation of the contents of a dashbord or look and does not
   * reflect the actual data displayed in the respective visualizations.
   * 
   */
  public void VectorThumbnail(VectorThumbnailRequest request, StreamObserver<VectorThumbnailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Content: Manage Content

  //#region Dashboard: Manage Dashboards

  /**   * ### Get information about all active dashboards.
   * 
   * Returns an array of **abbreviated dashboard objects**. Dashboards marked as deleted are excluded from this list.
   * 
   * Get the **full details** of a specific dashboard by id with [dashboard()](#!/Dashboard/dashboard)
   * 
   * Find **deleted dashboards** with [search_dashboards()](#!/Dashboard/search_dashboards)
   * 
   */
  public void AllDashboards(AllDashboardsRequest request, StreamObserver<AllDashboardsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new dashboard
   * 
   * Creates a new dashboard object and returns the details of the newly created dashboard.
   * 
   * `Title`, `user_id`, and `space_id` are all required fields.
   * `Space_id` and `user_id` must contain the id of an existing space or user, respectively.
   * A dashboard's `title` must be unique within the space in which it resides.
   * 
   * If you receive a 422 error response when creating a dashboard, be sure to look at the
   * response body for information about exactly which fields are missing or contain invalid data.
   * 
   * You can **update** an existing dashboard with [update_dashboard()](#!/Dashboard/update_dashboard)
   * 
   * You can **permanently delete** an existing dashboard with [delete_dashboard()](#!/Dashboard/delete_dashboard)
   * 
   */
  public void CreateDashboard(CreateDashboardRequest request, StreamObserver<CreateDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search Dashboards
   * 
   * Returns an **array of dashboard objects** that match the specified search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   * The parameters `limit`, and `offset` are recommended for fetching results in page-size chunks.
   * 
   * Get a **single dashboard** by id with [dashboard()](#!/Dashboard/dashboard)
   * 
   */
  public void SearchDashboards(SearchDashboardsRequest request, StreamObserver<SearchDashboardsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Import a LookML dashboard to a space as a UDD
   * Creates a UDD (a dashboard which exists in the Looker database rather than as a LookML file) from the LookML dashboard
   * and puts it in the space specified. The created UDD will have a lookml_link_id which links to the original LookML dashboard.
   * 
   * To give the imported dashboard specify a (e.g. title: "my title") in the body of your request, otherwise the imported
   * dashboard will have the same title as the original LookML dashboard.
   * 
   * For this operation to succeed the user must have permission to see the LookML dashboard in question, and have permission to
   * create content in the space the dashboard is being imported to.
   * 
   * **Sync** a linked UDD with [sync_lookml_dashboard()](#!/Dashboard/sync_lookml_dashboard)
   * **Unlink** a linked UDD by setting lookml_link_id to null with [update_dashboard()](#!/Dashboard/update_dashboard)
   * 
   */
  public void ImportLookmlDashboard(ImportLookmlDashboardRequest request, StreamObserver<ImportLookmlDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update all linked dashboards to match the specified LookML dashboard.
   * 
   * Any UDD (a dashboard which exists in the Looker database rather than as a LookML file) which has a `lookml_link_id`
   * property value referring to a LookML dashboard's id (model::dashboardname) will be updated so that it matches the current state of the LookML dashboard.
   * 
   * For this operation to succeed the user must have permission to view the LookML dashboard, and only linked dashboards
   * that the user has permission to update will be synced.
   * 
   * To **link** or **unlink** a UDD set the `lookml_link_id` property with [update_dashboard()](#!/Dashboard/update_dashboard)
   * 
   */
  public void SyncLookmlDashboard(SyncLookmlDashboardRequest request, StreamObserver<SyncLookmlDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a dashboard
   * 
   * Returns the full details of the identified dashboard object
   * 
   * Get a **summary list** of all active dashboards with [all_dashboards()](#!/Dashboard/all_dashboards)
   * 
   * You can **Search** for dashboards with [search_dashboards()](#!/Dashboard/search_dashboards)
   * 
   */
  public void Dashboard(DashboardRequest request, StreamObserver<DashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a dashboard
   * 
   * You can use this function to change the string and integer properties of
   * a dashboard. Nested objects such as filters, dashboard elements, or dashboard layout components
   * cannot be modified by this function - use the update functions for the respective
   * nested object types (like [update_dashboard_filter()](#!/3.1/Dashboard/update_dashboard_filter) to change a filter)
   * to modify nested objects referenced by a dashboard.
   * 
   * If you receive a 422 error response when updating a dashboard, be sure to look at the
   * response body for information about exactly which fields are missing or contain invalid data.
   * 
   */
  public void UpdateDashboard(UpdateDashboardRequest request, StreamObserver<UpdateDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the dashboard with the specified id
   * 
   * Permanently **deletes** a dashboard. (The dashboard cannot be recovered after this operation.)
   * 
   * "Soft" delete or hide a dashboard by setting its `deleted` status to `True` with [update_dashboard()](#!/Dashboard/update_dashboard).
   * 
   * Note: When a dashboard is deleted in the UI, it is soft deleted. Use this API call to permanently remove it, if desired.
   * 
   */
  public void DeleteDashboard(DeleteDashboardRequest request, StreamObserver<DeleteDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Aggregate Table LookML for Each Query on a Dahboard
   * 
   * Returns a JSON object that contains the dashboard id and Aggregate Table lookml
   * 
   * 
   */
  public void DashboardAggregateTableLookml(DashboardAggregateTableLookmlRequest request, StreamObserver<DashboardAggregateTableLookmlResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get lookml of a UDD
   * 
   * Returns a JSON object that contains the dashboard id and the full lookml
   * 
   * 
   */
  public void DashboardLookml(DashboardLookmlRequest request, StreamObserver<DashboardLookmlResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search Dashboard Elements
   * 
   * Returns an **array of DashboardElement objects** that match the specified search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchDashboardElements(SearchDashboardElementsRequest request, StreamObserver<SearchDashboardElementsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the dashboard element with a specific id.
   */
  public void DashboardElement(DashboardElementRequest request, StreamObserver<DashboardElementResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the dashboard element with a specific id.
   */
  public void UpdateDashboardElement(UpdateDashboardElementRequest request, StreamObserver<UpdateDashboardElementResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a dashboard element with a specific id.
   */
  public void DeleteDashboardElement(DeleteDashboardElementRequest request, StreamObserver<DeleteDashboardElementResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the dashboard elements on a dashboard with a specific id.
   */
  public void DashboardDashboardElements(DashboardDashboardElementsRequest request, StreamObserver<DashboardDashboardElementsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a dashboard element on the dashboard with a specific id.
   */
  public void CreateDashboardElement(CreateDashboardElementRequest request, StreamObserver<CreateDashboardElementResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the dashboard filters with a specific id.
   */
  public void DashboardFilter(DashboardFilterRequest request, StreamObserver<DashboardFilterResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the dashboard filter with a specific id.
   */
  public void UpdateDashboardFilter(UpdateDashboardFilterRequest request, StreamObserver<UpdateDashboardFilterResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a dashboard filter with a specific id.
   */
  public void DeleteDashboardFilter(DeleteDashboardFilterRequest request, StreamObserver<DeleteDashboardFilterResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the dashboard filters on a dashboard with a specific id.
   */
  public void DashboardDashboardFilters(DashboardDashboardFiltersRequest request, StreamObserver<DashboardDashboardFiltersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a dashboard filter on the dashboard with a specific id.
   */
  public void CreateDashboardFilter(CreateDashboardFilterRequest request, StreamObserver<CreateDashboardFilterResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the dashboard elements with a specific id.
   */
  public void DashboardLayoutComponent(DashboardLayoutComponentRequest request, StreamObserver<DashboardLayoutComponentResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the dashboard element with a specific id.
   */
  public void UpdateDashboardLayoutComponent(UpdateDashboardLayoutComponentRequest request, StreamObserver<UpdateDashboardLayoutComponentResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the dashboard layout components for a dashboard layout with a specific id.
   */
  public void DashboardLayoutDashboardLayoutComponents(DashboardLayoutDashboardLayoutComponentsRequest request, StreamObserver<DashboardLayoutDashboardLayoutComponentsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the dashboard layouts with a specific id.
   */
  public void DashboardLayout(DashboardLayoutRequest request, StreamObserver<DashboardLayoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the dashboard layout with a specific id.
   */
  public void UpdateDashboardLayout(UpdateDashboardLayoutRequest request, StreamObserver<UpdateDashboardLayoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a dashboard layout with a specific id.
   */
  public void DeleteDashboardLayout(DeleteDashboardLayoutRequest request, StreamObserver<DeleteDashboardLayoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the dashboard elements on a dashboard with a specific id.
   */
  public void DashboardDashboardLayouts(DashboardDashboardLayoutsRequest request, StreamObserver<DashboardDashboardLayoutsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a dashboard layout on the dashboard with a specific id.
   */
  public void CreateDashboardLayout(CreateDashboardLayoutRequest request, StreamObserver<CreateDashboardLayoutResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Dashboard: Manage Dashboards

  //#region DataAction: Run Data Actions

  /**   * Perform a data action. The data action object can be obtained from query results, and used to perform an arbitrary action.
   */
  public void PerformDataAction(PerformDataActionRequest request, StreamObserver<PerformDataActionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * For some data actions, the remote server may supply a form requesting further user input. This endpoint takes a data action, asks the remote server to generate a form for it, and returns that form to you for presentation to the user.
   */
  public void FetchRemoteDataActionForm(FetchRemoteDataActionFormRequest request, StreamObserver<FetchRemoteDataActionFormResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion DataAction: Run Data Actions

  //#region Datagroup: Manage Datagroups

  /**   * ### Get information about all datagroups.
   * 
   */
  public void AllDatagroups(AllDatagroupsRequest request, StreamObserver<AllDatagroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a datagroup.
   * 
   */
  public void Datagroup(DatagroupRequest request, StreamObserver<DatagroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a datagroup using the specified params.
   * 
   */
  public void UpdateDatagroup(UpdateDatagroupRequest request, StreamObserver<UpdateDatagroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Datagroup: Manage Datagroups

  //#region Folder: Manage Folders

  /**   * Search for folders by creator id, parent id, name, etc
   */
  public void SearchFolders(SearchFoldersRequest request, StreamObserver<SearchFoldersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the folder with a specific id.
   */
  public void Folder(FolderRequest request, StreamObserver<FolderResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the folder with a specific id.
   */
  public void UpdateFolder(UpdateFolderRequest request, StreamObserver<UpdateFolderResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the folder with a specific id including any children folders.
   * **DANGER** this will delete all looks and dashboards in the folder.
   * 
   */
  public void DeleteFolder(DeleteFolderRequest request, StreamObserver<DeleteFolderResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all folders.
   * 
   * In API 3.x, this will not return empty personal folders, unless they belong to the calling user.
   * In API 4.0+, all personal folders will be returned.
   * 
   * 
   */
  public void AllFolders(AllFoldersRequest request, StreamObserver<AllFoldersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a folder with specified information.
   * 
   * Caller must have permission to edit the parent folder and to create folders, otherwise the request
   * returns 404 Not Found.
   * 
   */
  public void CreateFolder(CreateFolderRequest request, StreamObserver<CreateFolderResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the children of a folder.
   */
  public void FolderChildren(FolderChildrenRequest request, StreamObserver<FolderChildrenResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search the children of a folder
   */
  public void FolderChildrenSearch(FolderChildrenSearchRequest request, StreamObserver<FolderChildrenSearchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the parent of a folder
   */
  public void FolderParent(FolderParentRequest request, StreamObserver<FolderParentResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the ancestors of a folder
   */
  public void FolderAncestors(FolderAncestorsRequest request, StreamObserver<FolderAncestorsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get all looks in a folder.
   * In API 3.x, this will return all looks in a folder, including looks in the trash.
   * In API 4.0+, all looks in a folder will be returned, excluding looks in the trash.
   * 
   */
  public void FolderLooks(FolderLooksRequest request, StreamObserver<FolderLooksResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the dashboards in a folder
   */
  public void FolderDashboards(FolderDashboardsRequest request, StreamObserver<FolderDashboardsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Folder: Manage Folders

  //#region Group: Manage Groups

  /**   * ### Get information about all groups.
   * 
   */
  public void AllGroups(AllGroupsRequest request, StreamObserver<AllGroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Creates a new group (admin only).
   * 
   */
  public void CreateGroup(CreateGroupRequest request, StreamObserver<CreateGroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search groups
   * 
   * Returns all group records that match the given search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchGroups(SearchGroupsRequest request, StreamObserver<SearchGroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search groups include roles
   * 
   * Returns all group records that match the given search criteria, and attaches any associated roles.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchGroupsWithRoles(SearchGroupsWithRolesRequest request, StreamObserver<SearchGroupsWithRolesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search groups include hierarchy
   * 
   * Returns all group records that match the given search criteria, and attaches
   * associated role_ids and parent group_ids.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchGroupsWithHierarchy(SearchGroupsWithHierarchyRequest request, StreamObserver<SearchGroupsWithHierarchyResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a group.
   * 
   */
  public void Group(GroupRequest request, StreamObserver<GroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Updates the a group (admin only).
   */
  public void UpdateGroup(UpdateGroupRequest request, StreamObserver<UpdateGroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Deletes a group (admin only).
   * 
   */
  public void DeleteGroup(DeleteGroupRequest request, StreamObserver<DeleteGroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the groups in a group
   * 
   */
  public void AllGroupGroups(AllGroupGroupsRequest request, StreamObserver<AllGroupGroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Adds a new group to a group.
   * 
   */
  public void AddGroupGroup(AddGroupGroupRequest request, StreamObserver<AddGroupGroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the users directly included in a group.
   * 
   */
  public void AllGroupUsers(AllGroupUsersRequest request, StreamObserver<AllGroupUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Adds a new user to a group.
   * 
   */
  public void AddGroupUser(AddGroupUserRequest request, StreamObserver<AddGroupUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Removes a user from a group.
   * 
   */
  public void DeleteGroupUser(DeleteGroupUserRequest request, StreamObserver<DeleteGroupUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Removes a group from a group.
   * 
   */
  public void DeleteGroupFromGroup(DeleteGroupFromGroupRequest request, StreamObserver<DeleteGroupFromGroupResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set the value of a user attribute for a group.
   * 
   * For information about how user attribute values are calculated, see [Set User Attribute Group Values](#!/UserAttribute/set_user_attribute_group_values).
   * 
   */
  public void UpdateUserAttributeGroupValue(UpdateUserAttributeGroupValueRequest request, StreamObserver<UpdateUserAttributeGroupValueResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Remove a user attribute value from a group.
   * 
   */
  public void DeleteUserAttributeGroupValue(DeleteUserAttributeGroupValueRequest request, StreamObserver<DeleteUserAttributeGroupValueResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Group: Manage Groups

  //#region Homepage: Manage Homepage

  /**   * ### Get information about the primary homepage's sections.
   * 
   */
  public void AllPrimaryHomepageSections(AllPrimaryHomepageSectionsRequest request, StreamObserver<AllPrimaryHomepageSectionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Homepage: Manage Homepage

  //#region Integration: Manage Integrations

  /**   * ### Get information about all Integration Hubs.
   * 
   */
  public void AllIntegrationHubs(AllIntegrationHubsRequest request, StreamObserver<AllIntegrationHubsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new Integration Hub.
   * 
   * This API is rate limited to prevent it from being used for SSRF attacks
   * 
   */
  public void CreateIntegrationHub(CreateIntegrationHubRequest request, StreamObserver<CreateIntegrationHubResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a Integration Hub.
   * 
   */
  public void IntegrationHub(IntegrationHubRequest request, StreamObserver<IntegrationHubResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a Integration Hub definition.
   * 
   * This API is rate limited to prevent it from being used for SSRF attacks
   * 
   */
  public void UpdateIntegrationHub(UpdateIntegrationHubRequest request, StreamObserver<UpdateIntegrationHubResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a Integration Hub.
   * 
   */
  public void DeleteIntegrationHub(DeleteIntegrationHubRequest request, StreamObserver<DeleteIntegrationHubResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Accepts the legal agreement for a given integration hub. This only works for integration hubs that have legal_agreement_required set to true and legal_agreement_signed set to false.
   */
  public void AcceptIntegrationHubLegalAgreement(AcceptIntegrationHubLegalAgreementRequest request, StreamObserver<AcceptIntegrationHubLegalAgreementResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all Integrations.
   * 
   */
  public void AllIntegrations(AllIntegrationsRequest request, StreamObserver<AllIntegrationsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a Integration.
   * 
   */
  public void Integration(IntegrationRequest request, StreamObserver<IntegrationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update parameters on a Integration.
   * 
   */
  public void UpdateIntegration(UpdateIntegrationRequest request, StreamObserver<UpdateIntegrationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Returns the Integration form for presentation to the user.
   */
  public void FetchIntegrationForm(FetchIntegrationFormRequest request, StreamObserver<FetchIntegrationFormResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Tests the integration to make sure all the settings are working.
   */
  public void TestIntegration(TestIntegrationRequest request, StreamObserver<TestIntegrationResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Integration: Manage Integrations

  //#region Look: Run and Manage Looks

  /**   * ### Get information about all active Looks
   * 
   * Returns an array of **abbreviated Look objects** describing all the looks that the caller has access to. Soft-deleted Looks are **not** included.
   * 
   * Get the **full details** of a specific look by id with [look(id)](#!/Look/look)
   * 
   * Find **soft-deleted looks** with [search_looks()](#!/Look/search_looks)
   * 
   */
  public void AllLooks(AllLooksRequest request, StreamObserver<AllLooksResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a Look
   * 
   * To create a look to display query data, first create the query with [create_query()](#!/Query/create_query)
   * then assign the query's id to the `query_id` property in the call to `create_look()`.
   * 
   * To place the look into a particular space, assign the space's id to the `space_id` property
   * in the call to `create_look()`.
   * 
   */
  public void CreateLook(CreateLookRequest request, StreamObserver<CreateLookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search Looks
   * 
   * Returns an **array of Look objects** that match the specified search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   * Get a **single look** by id with [look(id)](#!/Look/look)
   * 
   */
  public void SearchLooks(SearchLooksRequest request, StreamObserver<SearchLooksResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a Look.
   * 
   * Returns detailed information about a Look and its associated Query.
   * 
   * 
   */
  public void Look(LookRequest request, StreamObserver<LookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Modify a Look
   * 
   * Use this function to modify parts of a look. Property values given in a call to `update_look` are
   * applied to the existing look, so there's no need to include properties whose values are not changing.
   * It's best to specify only the properties you want to change and leave everything else out
   * of your `update_look` call. **Look properties marked 'read-only' will be ignored.**
   * 
   * When a user deletes a look in the Looker UI, the look data remains in the database but is
   * marked with a deleted flag ("soft-deleted"). Soft-deleted looks can be undeleted (by an admin)
   * if the delete was in error.
   * 
   * To soft-delete a look via the API, use [update_look()](#!/Look/update_look) to change the look's `deleted` property to `true`.
   * You can undelete a look by calling `update_look` to change the look's `deleted` property to `false`.
   * 
   * Soft-deleted looks are excluded from the results of [all_looks()](#!/Look/all_looks) and [search_looks()](#!/Look/search_looks), so they
   * essentially disappear from view even though they still reside in the db.
   * In API 3.1 and later, you can pass `deleted: true` as a parameter to [search_looks()](#!/3.1/Look/search_looks) to list soft-deleted looks.
   * 
   * NOTE: [delete_look()](#!/Look/delete_look) performs a "hard delete" - the look data is removed from the Looker
   * database and destroyed. There is no "undo" for `delete_look()`.
   * 
   */
  public void UpdateLook(UpdateLookRequest request, StreamObserver<UpdateLookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Permanently Delete a Look
   * 
   * This operation **permanently** removes a look from the Looker database.
   * 
   * NOTE: There is no "undo" for this kind of delete.
   * 
   * For information about soft-delete (which can be undone) see [update_look()](#!/Look/update_look).
   * 
   */
  public void DeleteLook(DeleteLookRequest request, StreamObserver<DeleteLookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run a Look
   * 
   * Runs a given look's query and returns the results in the requested format.
   * 
   * Supported formats:
   * 
   * | result_format | Description
   * | :-----------: | :--- |
   * | json | Plain json
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | md | Simple markdown
   * | xlsx | MS Excel spreadsheet
   * | sql | Returns the generated SQL rather than running the query
   * | png | A PNG image of the visualization of the query
   * | jpg | A JPG image of the visualization of the query
   * 
   * 
   * 
   */
  public void RunLook(RunLookRequest request, StreamObserver<RunLookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Look: Run and Manage Looks

  //#region LookmlModel: Manage LookML Models

  /**   * ### Get information about all lookml models.
   * 
   */
  public void AllLookmlModels(AllLookmlModelsRequest request, StreamObserver<AllLookmlModelsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a lookml model using the specified configuration.
   * 
   */
  public void CreateLookmlModel(CreateLookmlModelRequest request, StreamObserver<CreateLookmlModelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a lookml model.
   * 
   */
  public void LookmlModel(LookmlModelRequest request, StreamObserver<LookmlModelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a lookml model using the specified configuration.
   * 
   */
  public void UpdateLookmlModel(UpdateLookmlModelRequest request, StreamObserver<UpdateLookmlModelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a lookml model.
   * 
   */
  public void DeleteLookmlModel(DeleteLookmlModelRequest request, StreamObserver<DeleteLookmlModelResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a lookml model explore.
   * 
   */
  public void LookmlModelExplore(LookmlModelExploreRequest request, StreamObserver<LookmlModelExploreResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion LookmlModel: Manage LookML Models

  //#region Metadata: Connection Metadata Features

  /**   * ### Field name suggestions for a model and view
   * 
   * 
   */
  public void ModelFieldnameSuggestions(ModelFieldnameSuggestionsRequest request, StreamObserver<ModelFieldnameSuggestionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### List databases available to this connection
   * 
   * Certain dialects can support multiple databases per single connection.
   * If this connection supports multiple databases, the database names will be returned in an array.
   * 
   * Connections using dialects that do not support multiple databases will return an empty array.
   * 
   * **Note**: [Connection Features](#!/Metadata/connection_features) can be used to determine if a connection supports
   * multiple databases.
   * 
   */
  public void ConnectionDatabases(ConnectionDatabasesRequest request, StreamObserver<ConnectionDatabasesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Retrieve metadata features for this connection
   * 
   * Returns a list of feature names with `true` (available) or `false` (not available)
   * 
   * 
   */
  public void ConnectionFeatures(ConnectionFeaturesRequest request, StreamObserver<ConnectionFeaturesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the list of schemas and tables for a connection
   * 
   * 
   */
  public void ConnectionSchemas(ConnectionSchemasRequest request, StreamObserver<ConnectionSchemasResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the list of tables for a schema
   * 
   * For dialects that support multiple databases, optionally identify which to use. If not provided, the default
   * database for the connection will be used.
   * 
   * For dialects that do **not** support multiple databases, **do not use** the database parameter
   * 
   */
  public void ConnectionTables(ConnectionTablesRequest request, StreamObserver<ConnectionTablesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the columns (and therefore also the tables) in a specific schema
   * 
   * 
   */
  public void ConnectionColumns(ConnectionColumnsRequest request, StreamObserver<ConnectionColumnsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search a connection for columns matching the specified name
   * 
   * **Note**: `column_name` must be a valid column name. It is not a search pattern.
   * 
   */
  public void ConnectionSearchColumns(ConnectionSearchColumnsRequest request, StreamObserver<ConnectionSearchColumnsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Connection cost estimating
   * 
   * Assign a `sql` statement to the body of the request. e.g., for Ruby, `{sql: 'select * from users'}`
   * 
   * **Note**: If the connection's dialect has no support for cost estimates, an error will be returned
   * 
   */
  public void ConnectionCostEstimate(ConnectionCostEstimateRequest request, StreamObserver<ConnectionCostEstimateResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Metadata: Connection Metadata Features

  //#region Project: Manage Projects

  /**   *       ### Generate Lockfile for All LookML Dependencies
   * 
   *       Git must have been configured, must be in dev mode and deploy permission required
   * 
   *       Install_all is a two step process
   *       1. For each remote_dependency in a project the dependency manager will resolve any ambiguous ref.
   *       2. The project will then write out a lockfile including each remote_dependency with its resolved ref.
   * 
   * 
   */
  public void LockAll(LockAllRequest request, StreamObserver<LockAllResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get All Git Branches
   * 
   * Returns a list of git branches in the project repository
   * 
   */
  public void AllGitBranches(AllGitBranchesRequest request, StreamObserver<AllGitBranchesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the Current Git Branch
   * 
   * Returns the git branch currently checked out in the given project repository
   * 
   */
  public void GitBranch(GitBranchRequest request, StreamObserver<GitBranchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Checkout and/or reset --hard an existing Git Branch
   * 
   * Only allowed in development mode
   *   - Call `update_session` to select the 'dev' workspace.
   * 
   * Checkout an existing branch if name field is different from the name of the currently checked out branch.
   * 
   * Optionally specify a branch name, tag name or commit SHA to which the branch should be reset.
   *   **DANGER** hard reset will be force pushed to the remote. Unsaved changes and commits may be permanently lost.
   * 
   * 
   */
  public void UpdateGitBranch(UpdateGitBranchRequest request, StreamObserver<UpdateGitBranchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create and Checkout a Git Branch
   * 
   * Creates and checks out a new branch in the given project repository
   * Only allowed in development mode
   *   - Call `update_session` to select the 'dev' workspace.
   * 
   * Optionally specify a branch name, tag name or commit SHA as the start point in the ref field.
   *   If no ref is specified, HEAD of the current branch will be used as the start point for the new branch.
   * 
   * 
   */
  public void CreateGitBranch(CreateGitBranchRequest request, StreamObserver<CreateGitBranchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the specified Git Branch
   * 
   * Returns the git branch specified in branch_name path param if it exists in the given project repository
   * 
   */
  public void FindGitBranch(FindGitBranchRequest request, StreamObserver<FindGitBranchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the specified Git Branch
   * 
   * Delete git branch specified in branch_name path param from local and remote of specified project repository
   * 
   */
  public void DeleteGitBranch(DeleteGitBranchRequest request, StreamObserver<DeleteGitBranchResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Deploy a Remote Branch or Ref to Production
   * 
   * Git must have been configured and deploy permission required.
   * 
   * Deploy is a one/two step process
   * 1. If this is the first deploy of this project, create the production project with git repository.
   * 2. Pull the branch or ref into the production project.
   * 
   * Can only specify either a branch or a ref.
   * 
   * 
   */
  public void DeployRefToProduction(DeployRefToProductionRequest request, StreamObserver<DeployRefToProductionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Deploy LookML from this Development Mode Project to Production
   * 
   * Git must have been configured, must be in dev mode and deploy permission required
   * 
   * Deploy is a two / three step process:
   * 
   * 1. Push commits in current branch of dev mode project to the production branch (origin/master).
   *    Note a. This step is skipped in read-only projects.
   *    Note b. If this step is unsuccessful for any reason (e.g. rejected non-fastforward because production branch has
   *              commits not in current branch), subsequent steps will be skipped.
   * 2. If this is the first deploy of this project, create the production project with git repository.
   * 3. Pull the production branch into the production project.
   * 
   * 
   */
  public void DeployToProduction(DeployToProductionRequest request, StreamObserver<DeployToProductionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Reset a project to the revision of the project that is in production.
   * 
   * **DANGER** this will delete any changes that have not been pushed to a remote repository.
   * 
   */
  public void ResetProjectToProduction(ResetProjectToProductionRequest request, StreamObserver<ResetProjectToProductionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Reset a project development branch to the revision of the project that is on the remote.
   * 
   * **DANGER** this will delete any changes that have not been pushed to a remote repository.
   * 
   */
  public void ResetProjectToRemote(ResetProjectToRemoteRequest request, StreamObserver<ResetProjectToRemoteResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get All Projects
   * 
   * Returns all projects visible to the current user
   * 
   */
  public void AllProjects(AllProjectsRequest request, StreamObserver<AllProjectsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create A Project
   * 
   * dev mode required.
   * - Call `update_session` to select the 'dev' workspace.
   * 
   * `name` is required.
   * `git_remote_url` is not allowed. To configure Git for the newly created project, follow the instructions in `update_project`.
   * 
   * 
   */
  public void CreateProject(CreateProjectRequest request, StreamObserver<CreateProjectResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get A Project
   * 
   * Returns the project with the given project id
   * 
   */
  public void Project(ProjectRequest request, StreamObserver<ProjectResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update Project Configuration
   * 
   * Apply changes to a project's configuration.
   * 
   * 
   * #### Configuring Git for a Project
   * 
   * To set up a Looker project with a remote git repository, follow these steps:
   * 
   * 1. Call `update_session` to select the 'dev' workspace.
   * 1. Call `create_git_deploy_key` to create a new deploy key for the project
   * 1. Copy the deploy key text into the remote git repository's ssh key configuration
   * 1. Call `update_project` to set project's `git_remote_url` ()and `git_service_name`, if necessary).
   * 
   * When you modify a project's `git_remote_url`, Looker connects to the remote repository to fetch
   * metadata. The remote git repository MUST be configured with the Looker-generated deploy
   * key for this project prior to setting the project's `git_remote_url`.
   * 
   * To set up a Looker project with a git repository residing on the Looker server (a 'bare' git repo):
   * 
   * 1. Call `update_session` to select the 'dev' workspace.
   * 1. Call `update_project` setting `git_remote_url` to null and `git_service_name` to "bare".
   * 
   * 
   */
  public void UpdateProject(UpdateProjectRequest request, StreamObserver<UpdateProjectResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get A Projects Manifest object
   * 
   * Returns the project with the given project id
   * 
   */
  public void Manifest(ManifestRequest request, StreamObserver<ManifestResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Git Deploy Key
   * 
   * Returns the ssh public key previously created for a project's git repository.
   * 
   */
  public void GitDeployKey(GitDeployKeyRequest request, StreamObserver<GitDeployKeyResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create Git Deploy Key
   * 
   * Create a public/private key pair for authenticating ssh git requests from Looker to a remote git repository
   * for a particular Looker project.
   * 
   * Returns the public key of the generated ssh key pair.
   * 
   * Copy this public key to your remote git repository's ssh keys configuration so that the remote git service can
   * validate and accept git requests from the Looker server.
   * 
   */
  public void CreateGitDeployKey(CreateGitDeployKeyRequest request, StreamObserver<CreateGitDeployKeyResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Cached Project Validation Results
   * 
   * Returns the cached results of a previous project validation calculation, if any.
   * Returns http status 204 No Content if no validation results exist.
   * 
   * Validating the content of all the files in a project can be computationally intensive
   * for large projects. Use this API to simply fetch the results of the most recent
   * project validation rather than revalidating the entire project from scratch.
   * 
   * A value of `"stale": true` in the response indicates that the project has changed since
   * the cached validation results were computed. The cached validation results may no longer
   * reflect the current state of the project.
   * 
   */
  public void ProjectValidationResults(ProjectValidationResultsRequest request, StreamObserver<ProjectValidationResultsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Validate Project
   * 
   * Performs lint validation of all lookml files in the project.
   * Returns a list of errors found, if any.
   * 
   * Validating the content of all the files in a project can be computationally intensive
   * for large projects. For best performance, call `validate_project(project_id)` only
   * when you really want to recompute project validation. To quickly display the results of
   * the most recent project validation (without recomputing), use `project_validation_results(project_id)`
   * 
   */
  public void ValidateProject(ValidateProjectRequest request, StreamObserver<ValidateProjectResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Project Workspace
   * 
   * Returns information about the state of the project files in the currently selected workspace
   * 
   */
  public void ProjectWorkspace(ProjectWorkspaceRequest request, StreamObserver<ProjectWorkspaceResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get All Project Files
   * 
   * Returns a list of the files in the project
   * 
   */
  public void AllProjectFiles(AllProjectFilesRequest request, StreamObserver<AllProjectFilesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Project File Info
   * 
   * Returns information about a file in the project
   * 
   */
  public void ProjectFile(ProjectFileRequest request, StreamObserver<ProjectFileResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get All Git Connection Tests
   * 
   * dev mode required.
   *   - Call `update_session` to select the 'dev' workspace.
   * 
   * Returns a list of tests which can be run against a project's (or the dependency project for the provided remote_url) git connection. Call [Run Git Connection Test](#!/Project/run_git_connection_test) to execute each test in sequence.
   * 
   * Tests are ordered by increasing specificity. Tests should be run in the order returned because later tests require functionality tested by tests earlier in the test list.
   * 
   * For example, a late-stage test for write access is meaningless if connecting to the git server (an early test) is failing.
   * 
   */
  public void AllGitConnectionTests(AllGitConnectionTestsRequest request, StreamObserver<AllGitConnectionTestsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run a git connection test
   * 
   * Run the named test on the git service used by this project (or the dependency project for the provided remote_url) and return the result. This
   * is intended to help debug git connections when things do not work properly, to give
   * more helpful information about why a git url is not working with Looker.
   * 
   * Tests should be run in the order they are returned by [Get All Git Connection Tests](#!/Project/all_git_connection_tests).
   * 
   */
  public void RunGitConnectionTest(RunGitConnectionTestRequest request, StreamObserver<RunGitConnectionTestResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get All LookML Tests
   * 
   * Returns a list of tests which can be run to validate a project's LookML code and/or the underlying data,
   * optionally filtered by the file id.
   * Call [Run LookML Test](#!/Project/run_lookml_test) to execute tests.
   * 
   */
  public void AllLookmlTests(AllLookmlTestsRequest request, StreamObserver<AllLookmlTestsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run LookML Tests
   * 
   * Runs all tests in the project, optionally filtered by file, test, and/or model.
   * 
   */
  public void RunLookmlTest(RunLookmlTestRequest request, StreamObserver<RunLookmlTestResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Configure Repository Credential for a remote dependency
   * 
   * Admin required.
   * 
   * `root_project_id` is required.
   * `credential_id` is required.
   * 
   * 
   */
  public void UpdateRepositoryCredential(UpdateRepositoryCredentialRequest request, StreamObserver<UpdateRepositoryCredentialResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Repository Credential for a remote dependency
   * 
   * Admin required.
   * 
   * `root_project_id` is required.
   * `credential_id` is required.
   * 
   */
  public void DeleteRepositoryCredential(DeleteRepositoryCredentialRequest request, StreamObserver<DeleteRepositoryCredentialResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get all Repository Credentials for a project
   * 
   * `root_project_id` is required.
   * 
   */
  public void GetAllRepositoryCredentials(GetAllRepositoryCredentialsRequest request, StreamObserver<GetAllRepositoryCredentialsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Project: Manage Projects

  //#region Query: Run and Manage Queries

  /**   * ### Create an async query task
   * 
   * Creates a query task (job) to run a previously created query asynchronously. Returns a Query Task ID.
   * 
   * Use [query_task(query_task_id)](#!/Query/query_task) to check the execution status of the query task.
   * After the query task status reaches "Complete", use [query_task_results(query_task_id)](#!/Query/query_task_results) to fetch the results of the query.
   * 
   */
  public void CreateQueryTask(CreateQueryTaskRequest request, StreamObserver<CreateQueryTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Fetch results of multiple async queries
   * 
   * Returns the results of multiple async queries in one request.
   * 
   * For Query Tasks that are not completed, the response will include the execution status of the Query Task but will not include query results.
   * Query Tasks whose results have expired will have a status of 'expired'.
   * If the user making the API request does not have sufficient privileges to view a Query Task result, the result will have a status of 'missing'
   * 
   */
  public void QueryTaskMultiResults(QueryTaskMultiResultsRequest request, StreamObserver<QueryTaskMultiResultsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Query Task details
   * 
   * Use this function to check the status of an async query task. After the status
   * reaches "Complete", you can call [query_task_results(query_task_id)](#!/Query/query_task_results) to
   * retrieve the results of the query.
   * 
   * Use [create_query_task()](#!/Query/create_query_task) to create an async query task.
   * 
   */
  public void QueryTask(QueryTaskRequest request, StreamObserver<QueryTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Async Query Results
   * 
   * Returns the results of an async query task if the query has completed.
   * 
   * If the query task is still running or waiting to run, this function returns 204 No Content.
   * 
   * If the query task ID is invalid or the cached results of the query task have expired, this function returns 404 Not Found.
   * 
   * Use [query_task(query_task_id)](#!/Query/query_task) to check the execution status of the query task
   * Call query_task_results only after the query task status reaches "Complete".
   * 
   * You can also use [query_task_multi_results()](#!/Query/query_task_multi_results) retrieve the
   * results of multiple async query tasks at the same time.
   * 
   * #### SQL Error Handling:
   * If the query fails due to a SQL db error, how this is communicated depends on the result_format you requested in `create_query_task()`.
   * 
   * For `json_detail` result_format: `query_task_results()` will respond with HTTP status '200 OK' and db SQL error info
   * will be in the `errors` property of the response object. The 'data' property will be empty.
   * 
   * For all other result formats: `query_task_results()` will respond with HTTP status `400 Bad Request` and some db SQL error info
   * will be in the message of the 400 error response, but not as detailed as expressed in `json_detail.errors`.
   * These data formats can only carry row data, and error info is not row data.
   * 
   */
  public void QueryTaskResults(QueryTaskResultsRequest request, StreamObserver<QueryTaskResultsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a previously created query by id.
   * 
   * A Looker query object includes the various parameters that define a database query that has been run or
   * could be run in the future. These parameters include: model, view, fields, filters, pivots, etc.
   * Query *results* are not part of the query object.
   * 
   * Query objects are unique and immutable. Query objects are created automatically in Looker as users explore data.
   * Looker does not delete them; they become part of the query history. When asked to create a query for
   * any given set of parameters, Looker will first try to find an existing query object with matching
   * parameters and will only create a new object when an appropriate object can not be found.
   * 
   * This 'get' method is used to get the details about a query for a given id. See the other methods here
   * to 'create' and 'run' queries.
   * 
   * Note that some fields like 'filter_config' and 'vis_config' etc are specific to how the Looker UI
   * builds queries and visualizations and are not generally useful for API use. They are not required when
   * creating new queries and can usually just be ignored.
   * 
   * 
   */
  public void Query(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the query for a given query slug.
   * 
   * This returns the query for the 'slug' in a query share URL.
   * 
   * The 'slug' is a randomly chosen short string that is used as an alternative to the query's id value
   * for use in URLs etc. This method exists as a convenience to help you use the API to 'find' queries that
   * have been created using the Looker UI.
   * 
   * You can use the Looker explore page to build a query and then choose the 'Share' option to
   * show the share url for the query. Share urls generally look something like 'https://looker.yourcompany/x/vwGSbfc'.
   * The trailing 'vwGSbfc' is the share slug. You can pass that string to this api method to get details about the query.
   * Those details include the 'id' that you can use to run the query. Or, you can copy the query body
   * (perhaps with your own modification) and use that as the basis to make/run new queries.
   * 
   * This will also work with slugs from Looker explore urls like
   * 'https://looker.yourcompany/explore/ecommerce/orders?qid=aogBgL6o3cKK1jN3RoZl5s'. In this case
   * 'aogBgL6o3cKK1jN3RoZl5s' is the slug.
   * 
   */
  public void QueryForSlug(QueryForSlugRequest request, StreamObserver<QueryForSlugResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a query.
   * 
   * This allows you to create a new query that you can later run. Looker queries are immutable once created
   * and are not deleted. If you create a query that is exactly like an existing query then the existing query
   * will be returned and no new query will be created. Whether a new query is created or not, you can use
   * the 'id' in the returned query with the 'run' method.
   * 
   * The query parameters are passed as json in the body of the request.
   * 
   * 
   */
  public void CreateQuery(CreateQueryRequest request, StreamObserver<CreateQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run a saved query.
   * 
   * This runs a previously saved query. You can use this on a query that was generated in the Looker UI
   * or one that you have explicitly created using the API. You can also use a query 'id' from a saved 'Look'.
   * 
   * The 'result_format' parameter specifies the desired structure and format of the response.
   * 
   * Supported formats:
   * 
   * | result_format | Description
   * | :-----------: | :--- |
   * | json | Plain json
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | md | Simple markdown
   * | xlsx | MS Excel spreadsheet
   * | sql | Returns the generated SQL rather than running the query
   * | png | A PNG image of the visualization of the query
   * | jpg | A JPG image of the visualization of the query
   * 
   * 
   * 
   */
  public void RunQuery(RunQueryRequest request, StreamObserver<RunQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run the query that is specified inline in the posted body.
   * 
   * This allows running a query as defined in json in the posted body. This combines
   * the two actions of posting & running a query into one step.
   * 
   * Here is an example body in json:
   * ```
   * {
   *   "model":"thelook",
   *   "view":"inventory_items",
   *   "fields":["category.name","inventory_items.days_in_inventory_tier","products.count"],
   *   "filters":{"category.name":"socks"},
   *   "sorts":["products.count desc 0"],
   *   "limit":"500",
   *   "query_timezone":"America/Los_Angeles"
   * }
   * ```
   * 
   * When using the Ruby SDK this would be passed as a Ruby hash like:
   * ```
   * {
   *  :model=>"thelook",
   *  :view=>"inventory_items",
   *  :fields=>
   *   ["category.name",
   *    "inventory_items.days_in_inventory_tier",
   *    "products.count"],
   *  :filters=>{:"category.name"=>"socks"},
   *  :sorts=>["products.count desc 0"],
   *  :limit=>"500",
   *  :query_timezone=>"America/Los_Angeles",
   * }
   * ```
   * 
   * This will return the result of running the query in the format specified by the 'result_format' parameter.
   * 
   * Supported formats:
   * 
   * | result_format | Description
   * | :-----------: | :--- |
   * | json | Plain json
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | md | Simple markdown
   * | xlsx | MS Excel spreadsheet
   * | sql | Returns the generated SQL rather than running the query
   * | png | A PNG image of the visualization of the query
   * | jpg | A JPG image of the visualization of the query
   * 
   * 
   * 
   */
  public void RunInlineQuery(RunInlineQueryRequest request, StreamObserver<RunInlineQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run an URL encoded query.
   * 
   * This requires the caller to encode the specifiers for the query into the URL query part using
   * Looker-specific syntax as explained below.
   * 
   * Generally, you would want to use one of the methods that takes the parameters as json in the POST body
   * for creating and/or running queries. This method exists for cases where one really needs to encode the
   * parameters into the URL of a single 'GET' request. This matches the way that the Looker UI formats
   * 'explore' URLs etc.
   * 
   * The parameters here are very similar to the json body formatting except that the filter syntax is
   * tricky. Unfortunately, this format makes this method not currently callable via the 'Try it out!' button
   * in this documentation page. But, this is callable when creating URLs manually or when using the Looker SDK.
   * 
   * Here is an example inline query URL:
   * 
   * ```
   * https://looker.mycompany.com:19999/api/3.0/queries/models/thelook/views/inventory_items/run/json?fields=category.name,inventory_items.days_in_inventory_tier,products.count&f[category.name]=socks&sorts=products.count+desc+0&limit=500&query_timezone=America/Los_Angeles
   * ```
   * 
   * When invoking this endpoint with the Ruby SDK, pass the query parameter parts as a hash. The hash to match the above would look like:
   * 
   * ```ruby
   * query_params =
   * {
   *   :fields => "category.name,inventory_items.days_in_inventory_tier,products.count",
   *   :"f[category.name]" => "socks",
   *   :sorts => "products.count desc 0",
   *   :limit => "500",
   *   :query_timezone => "America/Los_Angeles"
   * }
   * response = ruby_sdk.run_url_encoded_query('thelook','inventory_items','json', query_params)
   * 
   * ```
   * 
   * Again, it is generally easier to use the variant of this method that passes the full query in the POST body.
   * This method is available for cases where other alternatives won't fit the need.
   * 
   * Supported formats:
   * 
   * | result_format | Description
   * | :-----------: | :--- |
   * | json | Plain json
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | md | Simple markdown
   * | xlsx | MS Excel spreadsheet
   * | sql | Returns the generated SQL rather than running the query
   * | png | A PNG image of the visualization of the query
   * | jpg | A JPG image of the visualization of the query
   * 
   * 
   * 
   */
  public void RunUrlEncodedQuery(RunUrlEncodedQueryRequest request, StreamObserver<RunUrlEncodedQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Merge Query
   * 
   * Returns a merge query object given its id.
   * 
   */
  public void MergeQuery(MergeQueryRequest request, StreamObserver<MergeQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create Merge Query
   * 
   * Creates a new merge query object.
   * 
   * A merge query takes the results of one or more queries and combines (merges) the results
   * according to field mapping definitions. The result is similar to a SQL left outer join.
   * 
   * A merge query can merge results of queries from different SQL databases.
   * 
   * The order that queries are defined in the source_queries array property is significant. The
   * first query in the array defines the primary key into which the results of subsequent
   * queries will be merged.
   * 
   * Like model/view query objects, merge queries are immutable and have structural identity - if
   * you make a request to create a new merge query that is identical to an existing merge query,
   * the existing merge query will be returned instead of creating a duplicate. Conversely, any
   * change to the contents of a merge query will produce a new object with a new id.
   * 
   */
  public void CreateMergeQuery(CreateMergeQueryRequest request, StreamObserver<CreateMergeQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Get information about all running queries.
   * 
   */
  public void AllRunningQueries(AllRunningQueriesRequest request, StreamObserver<AllRunningQueriesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Kill a query with a specific query_task_id.
   * 
   */
  public void KillQuery(KillQueryRequest request, StreamObserver<KillQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Get a SQL Runner query.
   */
  public void SqlQuery(SqlQueryRequest request, StreamObserver<SqlQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a SQL Runner Query
   * 
   * Either the `connection_name` or `model_name` parameter MUST be provided.
   * 
   */
  public void CreateSqlQuery(CreateSqlQueryRequest request, StreamObserver<CreateSqlQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * Execute a SQL Runner query in a given result_format.
   */
  public void RunSqlQuery(RunSqlQueryRequest request, StreamObserver<RunSqlQueryResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Query: Run and Manage Queries

  //#region RenderTask: Manage Render Tasks

  /**   * ### Create a new task to render a look to an image.
   * 
   * Returns a render task object.
   * To check the status of a render task, pass the render_task.id to [Get Render Task](#!/RenderTask/get_render_task).
   * Once the render task is complete, you can download the resulting document or image using [Get Render Task Results](#!/RenderTask/get_render_task_results).
   * 
   * 
   */
  public void CreateLookRenderTask(CreateLookRenderTaskRequest request, StreamObserver<CreateLookRenderTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new task to render an existing query to an image.
   * 
   * Returns a render task object.
   * To check the status of a render task, pass the render_task.id to [Get Render Task](#!/RenderTask/get_render_task).
   * Once the render task is complete, you can download the resulting document or image using [Get Render Task Results](#!/RenderTask/get_render_task_results).
   * 
   * 
   */
  public void CreateQueryRenderTask(CreateQueryRenderTaskRequest request, StreamObserver<CreateQueryRenderTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new task to render a dashboard to a document or image.
   * 
   * Returns a render task object.
   * To check the status of a render task, pass the render_task.id to [Get Render Task](#!/RenderTask/get_render_task).
   * Once the render task is complete, you can download the resulting document or image using [Get Render Task Results](#!/RenderTask/get_render_task_results).
   * 
   * 
   */
  public void CreateDashboardRenderTask(CreateDashboardRenderTaskRequest request, StreamObserver<CreateDashboardRenderTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a render task.
   * 
   * Returns a render task object.
   * To check the status of a render task, pass the render_task.id to [Get Render Task](#!/RenderTask/get_render_task).
   * Once the render task is complete, you can download the resulting document or image using [Get Render Task Results](#!/RenderTask/get_render_task_results).
   * 
   * 
   */
  public void RenderTask(RenderTaskRequest request, StreamObserver<RenderTaskResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the document or image produced by a completed render task.
   * 
   * Note that the PDF or image result will be a binary blob in the HTTP response, as indicated by the
   * Content-Type in the response headers. This may require specialized (or at least different) handling than text
   * responses such as JSON. You may need to tell your HTTP client that the response is binary so that it does not
   * attempt to parse the binary data as text.
   * 
   * If the render task exists but has not finished rendering the results, the response HTTP status will be
   * **202 Accepted**, the response body will be empty, and the response will have a Retry-After header indicating
   * that the caller should repeat the request at a later time.
   * 
   * Returns 404 if the render task cannot be found, if the cached result has expired, or if the caller
   * does not have permission to view the results.
   * 
   * For detailed information about the status of the render task, use [Render Task](#!/RenderTask/render_task).
   * Polling loops waiting for completion of a render task would be better served by polling **render_task(id)** until
   * the task status reaches completion (or error) instead of polling **render_task_results(id)** alone.
   * 
   */
  public void RenderTaskResults(RenderTaskResultsRequest request, StreamObserver<RenderTaskResultsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion RenderTask: Manage Render Tasks

  //#region Role: Manage Roles

  /**   * ### Search model sets
   * Returns all model set records that match the given search criteria.
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchModelSets(SearchModelSetsRequest request, StreamObserver<SearchModelSetsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the model set with a specific id.
   * 
   */
  public void ModelSet(ModelSetRequest request, StreamObserver<ModelSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update information about the model set with a specific id.
   * 
   */
  public void UpdateModelSet(UpdateModelSetRequest request, StreamObserver<UpdateModelSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the model set with a specific id.
   * 
   */
  public void DeleteModelSet(DeleteModelSetRequest request, StreamObserver<DeleteModelSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all model sets.
   * 
   */
  public void AllModelSets(AllModelSetsRequest request, StreamObserver<AllModelSetsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a model set with the specified information. Model sets are used by Roles.
   * 
   */
  public void CreateModelSet(CreateModelSetRequest request, StreamObserver<CreateModelSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get all supported permissions.
   * 
   */
  public void AllPermissions(AllPermissionsRequest request, StreamObserver<AllPermissionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search permission sets
   * Returns all permission set records that match the given search criteria.
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchPermissionSets(SearchPermissionSetsRequest request, StreamObserver<SearchPermissionSetsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the permission set with a specific id.
   * 
   */
  public void PermissionSet(PermissionSetRequest request, StreamObserver<PermissionSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update information about the permission set with a specific id.
   * 
   */
  public void UpdatePermissionSet(UpdatePermissionSetRequest request, StreamObserver<UpdatePermissionSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the permission set with a specific id.
   * 
   */
  public void DeletePermissionSet(DeletePermissionSetRequest request, StreamObserver<DeletePermissionSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all permission sets.
   * 
   */
  public void AllPermissionSets(AllPermissionSetsRequest request, StreamObserver<AllPermissionSetsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a permission set with the specified information. Permission sets are used by Roles.
   * 
   */
  public void CreatePermissionSet(CreatePermissionSetRequest request, StreamObserver<CreatePermissionSetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all roles.
   * 
   */
  public void AllRoles(AllRolesRequest request, StreamObserver<AllRolesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a role with the specified information.
   * 
   */
  public void CreateRole(CreateRoleRequest request, StreamObserver<CreateRoleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search roles
   * 
   * Returns all role records that match the given search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   */
  public void SearchRoles(SearchRolesRequest request, StreamObserver<SearchRolesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the role with a specific id.
   * 
   */
  public void Role(RoleRequest request, StreamObserver<RoleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update information about the role with a specific id.
   * 
   */
  public void UpdateRole(UpdateRoleRequest request, StreamObserver<UpdateRoleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the role with a specific id.
   * 
   */
  public void DeleteRole(DeleteRoleRequest request, StreamObserver<DeleteRoleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the groups with the role that has a specific id.
   * 
   */
  public void RoleGroups(RoleGroupsRequest request, StreamObserver<RoleGroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set all groups for a role, removing all existing group associations from that role.
   * 
   */
  public void SetRoleGroups(SetRoleGroupsRequest request, StreamObserver<SetRoleGroupsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all the users with the role that has a specific id.
   * 
   */
  public void RoleUsers(RoleUsersRequest request, StreamObserver<RoleUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set all the users of the role with a specific id.
   * 
   */
  public void SetRoleUsers(SetRoleUsersRequest request, StreamObserver<SetRoleUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Role: Manage Roles

  //#region ScheduledPlan: Manage Scheduled Plans

  /**   * ### Get Scheduled Plans for a Space
   * 
   * Returns scheduled plans owned by the caller for a given space id.
   * 
   */
  public void ScheduledPlansForSpace(ScheduledPlansForSpaceRequest request, StreamObserver<ScheduledPlansForSpaceResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Information About a Scheduled Plan
   * 
   * Admins can fetch information about other users' Scheduled Plans.
   * 
   */
  public void ScheduledPlan(ScheduledPlanRequest request, StreamObserver<ScheduledPlanResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a Scheduled Plan
   * 
   * Admins can update other users' Scheduled Plans.
   * 
   * Note: Any scheduled plan destinations specified in an update will **replace** all scheduled plan destinations
   * currently defined for the scheduled plan.
   * 
   * For Example: If a scheduled plan has destinations A, B, and C, and you call update on this scheduled plan
   * specifying only B in the destinations, then destinations A and C will be deleted by the update.
   * 
   * Updating a scheduled plan to assign null or an empty array to the scheduled_plan_destinations property is an error, as a scheduled plan must always have at least one destination.
   * 
   * If you omit the scheduled_plan_destinations property from the object passed to update, then the destinations
   * defined on the original scheduled plan will remain unchanged.
   * 
   * #### Email Permissions:
   * 
   * For details about permissions required to schedule delivery to email and the safeguards
   * Looker offers to protect against sending to unauthorized email destinations, see [Email Domain Whitelist for Scheduled Looks](https://docs.looker.com/r/api/embed-permissions).
   * 
   * 
   * #### Scheduled Plan Destination Formats
   * 
   * Scheduled plan destinations must specify the data format to produce and send to the destination.
   * 
   * Formats:
   * 
   * | format | Description
   * | :-----------: | :--- |
   * | json | A JSON object containing a `data` property which contains an array of JSON objects, one per row. No metadata.
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | inline_json | Same as the JSON format, except that the `data` property is a string containing JSON-escaped row data. Additional properties describe the data operation. This format is primarily used to send data to web hooks so that the web hook doesn't have to re-encode the JSON row data in order to pass it on to its ultimate destination.
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | xlsx | MS Excel spreadsheet
   * | wysiwyg_pdf | Dashboard rendered in a tiled layout to produce a PDF document
   * | assembled_pdf | Dashboard rendered in a single column layout to produce a PDF document
   * | wysiwyg_png | Dashboard rendered in a tiled layout to produce a PNG image
   * ||
   * 
   * Valid formats vary by destination type and source object. `wysiwyg_pdf` is only valid for dashboards, for example.
   * 
   * 
   * 
   */
  public void UpdateScheduledPlan(UpdateScheduledPlanRequest request, StreamObserver<UpdateScheduledPlanResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a Scheduled Plan
   * 
   * Normal users can only delete their own scheduled plans.
   * Admins can delete other users' scheduled plans.
   * This delete cannot be undone.
   * 
   */
  public void DeleteScheduledPlan(DeleteScheduledPlanRequest request, StreamObserver<DeleteScheduledPlanResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### List All Scheduled Plans
   * 
   * Returns all scheduled plans which belong to the caller or given user.
   * 
   * If no user_id is provided, this function returns the scheduled plans owned by the caller.
   * 
   * 
   * To list all schedules for all users, pass `all_users=true`.
   * 
   * 
   * The caller must have `see_schedules` permission to see other users' scheduled plans.
   * 
   * 
   * 
   */
  public void AllScheduledPlans(AllScheduledPlansRequest request, StreamObserver<AllScheduledPlansResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a Scheduled Plan
   * 
   * Create a scheduled plan to render a Look or Dashboard on a recurring schedule.
   * 
   * To create a scheduled plan, you MUST provide values for the following fields:
   * `name`
   * and
   * `look_id`, `dashboard_id`, `lookml_dashboard_id`, or `query_id`
   * and
   * `cron_tab` or `datagroup`
   * and
   * at least one scheduled_plan_destination
   * 
   * A scheduled plan MUST have at least one scheduled_plan_destination defined.
   * 
   * When `look_id` is set, `require_no_results`, `require_results`, and `require_change` are all required.
   * 
   * If `create_scheduled_plan` fails with a 422 error, be sure to look at the error messages in the response which will explain exactly what fields are missing or values that are incompatible.
   * 
   * The queries that provide the data for the look or dashboard are run in the context of user account that owns the scheduled plan.
   * 
   * When `run_as_recipient` is `false` or not specified, the queries that provide the data for the
   * look or dashboard are run in the context of user account that owns the scheduled plan.
   * 
   * When `run_as_recipient` is `true` and all the email recipients are Looker user accounts, the
   * queries are run in the context of each recipient, so different recipients may see different
   * data from the same scheduled render of a look or dashboard. For more details, see [Run As Recipient](https://looker.com/docs/r/admin/run-as-recipient).
   * 
   * Admins can create and modify scheduled plans on behalf of other users by specifying a user id.
   * Non-admin users may not create or modify scheduled plans by or for other users.
   * 
   * #### Email Permissions:
   * 
   * For details about permissions required to schedule delivery to email and the safeguards
   * Looker offers to protect against sending to unauthorized email destinations, see [Email Domain Whitelist for Scheduled Looks](https://docs.looker.com/r/api/embed-permissions).
   * 
   * 
   * #### Scheduled Plan Destination Formats
   * 
   * Scheduled plan destinations must specify the data format to produce and send to the destination.
   * 
   * Formats:
   * 
   * | format | Description
   * | :-----------: | :--- |
   * | json | A JSON object containing a `data` property which contains an array of JSON objects, one per row. No metadata.
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | inline_json | Same as the JSON format, except that the `data` property is a string containing JSON-escaped row data. Additional properties describe the data operation. This format is primarily used to send data to web hooks so that the web hook doesn't have to re-encode the JSON row data in order to pass it on to its ultimate destination.
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | xlsx | MS Excel spreadsheet
   * | wysiwyg_pdf | Dashboard rendered in a tiled layout to produce a PDF document
   * | assembled_pdf | Dashboard rendered in a single column layout to produce a PDF document
   * | wysiwyg_png | Dashboard rendered in a tiled layout to produce a PNG image
   * ||
   * 
   * Valid formats vary by destination type and source object. `wysiwyg_pdf` is only valid for dashboards, for example.
   * 
   * 
   * 
   */
  public void CreateScheduledPlan(CreateScheduledPlanRequest request, StreamObserver<CreateScheduledPlanResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run a Scheduled Plan Immediately
   * 
   * Create a scheduled plan that runs only once, and immediately.
   * 
   * This can be useful for testing a Scheduled Plan before committing to a production schedule.
   * 
   * Admins can create scheduled plans on behalf of other users by specifying a user id.
   * 
   * This API is rate limited to prevent it from being used for relay spam or DoS attacks
   * 
   * #### Email Permissions:
   * 
   * For details about permissions required to schedule delivery to email and the safeguards
   * Looker offers to protect against sending to unauthorized email destinations, see [Email Domain Whitelist for Scheduled Looks](https://docs.looker.com/r/api/embed-permissions).
   * 
   * 
   * #### Scheduled Plan Destination Formats
   * 
   * Scheduled plan destinations must specify the data format to produce and send to the destination.
   * 
   * Formats:
   * 
   * | format | Description
   * | :-----------: | :--- |
   * | json | A JSON object containing a `data` property which contains an array of JSON objects, one per row. No metadata.
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | inline_json | Same as the JSON format, except that the `data` property is a string containing JSON-escaped row data. Additional properties describe the data operation. This format is primarily used to send data to web hooks so that the web hook doesn't have to re-encode the JSON row data in order to pass it on to its ultimate destination.
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | xlsx | MS Excel spreadsheet
   * | wysiwyg_pdf | Dashboard rendered in a tiled layout to produce a PDF document
   * | assembled_pdf | Dashboard rendered in a single column layout to produce a PDF document
   * | wysiwyg_png | Dashboard rendered in a tiled layout to produce a PNG image
   * ||
   * 
   * Valid formats vary by destination type and source object. `wysiwyg_pdf` is only valid for dashboards, for example.
   * 
   * 
   * 
   */
  public void ScheduledPlanRunOnce(ScheduledPlanRunOnceRequest request, StreamObserver<ScheduledPlanRunOnceResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Scheduled Plans for a Look
   * 
   * Returns all scheduled plans for a look which belong to the caller or given user.
   * 
   * If no user_id is provided, this function returns the scheduled plans owned by the caller.
   * 
   * 
   * To list all schedules for all users, pass `all_users=true`.
   * 
   * 
   * The caller must have `see_schedules` permission to see other users' scheduled plans.
   * 
   * 
   * 
   */
  public void ScheduledPlansForLook(ScheduledPlansForLookRequest request, StreamObserver<ScheduledPlansForLookResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Scheduled Plans for a Dashboard
   * 
   * Returns all scheduled plans for a dashboard which belong to the caller or given user.
   * 
   * If no user_id is provided, this function returns the scheduled plans owned by the caller.
   * 
   * 
   * To list all schedules for all users, pass `all_users=true`.
   * 
   * 
   * The caller must have `see_schedules` permission to see other users' scheduled plans.
   * 
   * 
   * 
   */
  public void ScheduledPlansForDashboard(ScheduledPlansForDashboardRequest request, StreamObserver<ScheduledPlansForDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get Scheduled Plans for a LookML Dashboard
   * 
   * Returns all scheduled plans for a LookML Dashboard which belong to the caller or given user.
   * 
   * If no user_id is provided, this function returns the scheduled plans owned by the caller.
   * 
   * 
   * To list all schedules for all users, pass `all_users=true`.
   * 
   * 
   * The caller must have `see_schedules` permission to see other users' scheduled plans.
   * 
   * 
   * 
   */
  public void ScheduledPlansForLookmlDashboard(ScheduledPlansForLookmlDashboardRequest request, StreamObserver<ScheduledPlansForLookmlDashboardResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Run a Scheduled Plan By Id Immediately
   * This function creates a run-once schedule plan based on an existing scheduled plan,
   * applies modifications (if any) to the new scheduled plan, and runs the new schedule plan immediately.
   * This can be useful for testing modifications to an existing scheduled plan before committing to a production schedule.
   * 
   * This function internally performs the following operations:
   * 
   * 1. Copies the properties of the existing scheduled plan into a new scheduled plan
   * 2. Copies any properties passed in the JSON body of this request into the new scheduled plan (replacing the original values)
   * 3. Creates the new scheduled plan
   * 4. Runs the new scheduled plan
   * 
   * The original scheduled plan is not modified by this operation.
   * Admins can create, modify, and run scheduled plans on behalf of other users by specifying a user id.
   * Non-admins can only create, modify, and run their own scheduled plans.
   * 
   * #### Email Permissions:
   * 
   * For details about permissions required to schedule delivery to email and the safeguards
   * Looker offers to protect against sending to unauthorized email destinations, see [Email Domain Whitelist for Scheduled Looks](https://docs.looker.com/r/api/embed-permissions).
   * 
   * 
   * #### Scheduled Plan Destination Formats
   * 
   * Scheduled plan destinations must specify the data format to produce and send to the destination.
   * 
   * Formats:
   * 
   * | format | Description
   * | :-----------: | :--- |
   * | json | A JSON object containing a `data` property which contains an array of JSON objects, one per row. No metadata.
   * | json_detail | Row data plus metadata describing the fields, pivots, table calcs, and other aspects of the query
   * | inline_json | Same as the JSON format, except that the `data` property is a string containing JSON-escaped row data. Additional properties describe the data operation. This format is primarily used to send data to web hooks so that the web hook doesn't have to re-encode the JSON row data in order to pass it on to its ultimate destination.
   * | csv | Comma separated values with a header
   * | txt | Tab separated values with a header
   * | html | Simple html
   * | xlsx | MS Excel spreadsheet
   * | wysiwyg_pdf | Dashboard rendered in a tiled layout to produce a PDF document
   * | assembled_pdf | Dashboard rendered in a single column layout to produce a PDF document
   * | wysiwyg_png | Dashboard rendered in a tiled layout to produce a PNG image
   * ||
   * 
   * Valid formats vary by destination type and source object. `wysiwyg_pdf` is only valid for dashboards, for example.
   * 
   * 
   * 
   * This API is rate limited to prevent it from being used for relay spam or DoS attacks
   * 
   * 
   */
  public void ScheduledPlanRunOnceById(ScheduledPlanRunOnceByIdRequest request, StreamObserver<ScheduledPlanRunOnceByIdResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion ScheduledPlan: Manage Scheduled Plans

  //#region Session: Session Information

  /**   * ### Get API Session
   * 
   * Returns information about the current API session, such as which workspace is selected for the session.
   * 
   */
  public void Session(SessionRequest request, StreamObserver<SessionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update API Session
   * 
   * #### API Session Workspace
   * 
   * You can use this endpoint to change the active workspace for the current API session.
   * 
   * Only one workspace can be active in a session. The active workspace can be changed
   * any number of times in a session.
   * 
   * The default workspace for API sessions is the "production" workspace.
   * 
   * All Looker APIs that use projects or lookml models (such as running queries) will
   * use the version of project and model files defined by this workspace for the lifetime of the
   * current API session or until the session workspace is changed again.
   * 
   * An API session has the same lifetime as the access_token used to authenticate API requests. Each successful
   * API login generates a new access_token and a new API session.
   * 
   * If your Looker API client application needs to work in a dev workspace across multiple
   * API sessions, be sure to select the dev workspace after each login.
   * 
   */
  public void UpdateSession(UpdateSessionRequest request, StreamObserver<UpdateSessionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Session: Session Information

  //#region Theme: Manage Themes

  /**   * ### Get an array of all existing themes
   * 
   * Get a **single theme** by id with [Theme](#!/Theme/theme)
   * 
   * This method returns an array of all existing themes. The active time for the theme is not considered.
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void AllThemes(AllThemesRequest request, StreamObserver<AllThemesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a theme
   * 
   * Creates a new theme object, returning the theme details, including the created id.
   * 
   * If `settings` are not specified, the default theme settings will be copied into the new theme.
   * 
   * The theme `name` can only contain alphanumeric characters or underscores. Theme names should not contain any confidential information, such as customer names.
   * 
   * **Update** an existing theme with [Update Theme](#!/Theme/update_theme)
   * 
   * **Permanently delete** an existing theme with [Delete Theme](#!/Theme/delete_theme)
   * 
   * For more information, see [Creating and Applying Themes](https://looker.com/docs/r/admin/themes).
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void CreateTheme(CreateThemeRequest request, StreamObserver<CreateThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search all themes for matching criteria.
   * 
   * Returns an **array of theme objects** that match the specified search criteria.
   * 
   * | Search Parameters | Description
   * | :-------------------: | :------ |
   * | `begin_at` only | Find themes active at or after `begin_at`
   * | `end_at` only | Find themes active at or before `end_at`
   * | both set | Find themes with an active inclusive period between `begin_at` and `end_at`
   * 
   * Note: Range matching requires boolean AND logic.
   * When using `begin_at` and `end_at` together, do not use `filter_or`=TRUE
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   * Get a **single theme** by id with [Theme](#!/Theme/theme)
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void SearchThemes(SearchThemesRequest request, StreamObserver<SearchThemesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the default theme
   * 
   * Returns the active theme object set as the default.
   * 
   * The **default** theme name can be set in the UI on the Admin|Theme UI page
   * 
   * The optional `ts` parameter can specify a different timestamp than "now." If specified, it returns the default theme at the time indicated.
   * 
   */
  public void DefaultTheme(DefaultThemeRequest request, StreamObserver<DefaultThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set the global default theme by theme name
   * 
   * Only Admin users can call this function.
   * 
   * Only an active theme with no expiration (`end_at` not set) can be assigned as the default theme. As long as a theme has an active record with no expiration, it can be set as the default.
   * 
   * [Create Theme](#!/Theme/create) has detailed information on rules for default and active themes
   * 
   * Returns the new specified default theme object.
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void SetDefaultTheme(SetDefaultThemeRequest request, StreamObserver<SetDefaultThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get active themes
   * 
   * Returns an array of active themes.
   * 
   * If the `name` parameter is specified, it will return an array with one theme if it's active and found.
   * 
   * The optional `ts` parameter can specify a different timestamp than "now."
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   * 
   */
  public void ActiveThemes(ActiveThemesRequest request, StreamObserver<ActiveThemesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get the named theme if it's active. Otherwise, return the default theme
   * 
   * The optional `ts` parameter can specify a different timestamp than "now."
   * Note: API users with `show` ability can call this function
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void ThemeOrDefault(ThemeOrDefaultRequest request, StreamObserver<ThemeOrDefaultResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Validate a theme with the specified information
   * 
   * Validates all values set for the theme, returning any errors encountered, or 200 OK if valid
   * 
   * See [Create Theme](#!/Theme/create_theme) for constraints
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void ValidateTheme(ValidateThemeRequest request, StreamObserver<ValidateThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get a theme by ID
   * 
   * Use this to retrieve a specific theme, whether or not it's currently active.
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void Theme(ThemeRequest request, StreamObserver<ThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update the theme by id.
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void UpdateTheme(UpdateThemeRequest request, StreamObserver<UpdateThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a specific theme by id
   * 
   * This operation permanently deletes the identified theme from the database.
   * 
   * Because multiple themes can have the same name (with different activation time spans) themes can only be deleted by ID.
   * 
   * All IDs associated with a theme name can be retrieved by searching for the theme name with [Theme Search](#!/Theme/search).
   * 
   * **Note**: Custom themes needs to be enabled by Looker. Unless custom themes are enabled, only the automatically generated default theme can be used. Please contact your Account Manager or help.looker.com to update your license for this feature.
   * 
   * 
   */
  public void DeleteTheme(DeleteThemeRequest request, StreamObserver<DeleteThemeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Theme: Manage Themes

  //#region User: Manage Users

  /**   * ### Get information about the current user; i.e. the user account currently calling the API.
   * 
   */
  public void Me(MeRequest request, StreamObserver<MeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about all users.
   * 
   */
  public void AllUsers(AllUsersRequest request, StreamObserver<AllUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a user with the specified information.
   * 
   */
  public void CreateUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search users
   * 
   * Returns all<sup>*</sup> user records that match the given search criteria.
   * 
   * If multiple search params are given and `filter_or` is FALSE or not specified,
   * search params are combined in a logical AND operation.
   * Only rows that match *all* search param criteria will be returned.
   * 
   * If `filter_or` is TRUE, multiple search params are combined in a logical OR operation.
   * Results will include rows that match **any** of the search criteria.
   * 
   * String search params use case-insensitive matching.
   * String search params can contain `%` and '_' as SQL LIKE pattern match wildcard expressions.
   * example="dan%" will match "danger" and "Danzig" but not "David"
   * example="D_m%" will match "Damage" and "dump"
   * 
   * Integer search params can accept a single value or a comma separated list of values. The multiple
   * values will be combined under a logical OR operation - results will match at least one of
   * the given values.
   * 
   * Most search params can accept "IS NULL" and "NOT NULL" as special expressions to match
   * or exclude (respectively) rows where the column is null.
   * 
   * Boolean search params accept only "true" and "false" as values.
   * 
   * 
   * (<sup>*</sup>) Results are always filtered to the level of information the caller is permitted to view.
   * Looker admins can see all user details; normal users in an open system can see
   * names of other users but no details; normal users in a closed system can only see
   * names of other users who are members of the same group as the user.
   * 
   * 
   */
  public void SearchUsers(SearchUsersRequest request, StreamObserver<SearchUsersResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Search for user accounts by name
   * 
   * Returns all user accounts where `first_name` OR `last_name` OR `email` field values match a pattern.
   * The pattern can contain `%` and `_` wildcards as in SQL LIKE expressions.
   * 
   * Any additional search params will be combined into a logical AND expression.
   * 
   */
  public void SearchUsersNames(SearchUsersNamesRequest request, StreamObserver<SearchUsersNamesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the user with a specific id.
   * 
   * If the caller is an admin or the caller is the user being specified, then full user information will
   * be returned. Otherwise, a minimal 'public' variant of the user information will be returned. This contains
   * The user name and avatar url, but no sensitive information.
   * 
   */
  public void User(UserRequest request, StreamObserver<UserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update information about the user with a specific id.
   * 
   */
  public void UpdateUser(UpdateUserRequest request, StreamObserver<UpdateUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete the user with a specific id.
   * 
   * **DANGER** this will delete the user and all looks and other information owned by the user.
   * 
   */
  public void DeleteUser(DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about the user with a credential of given type with specific id.
   * 
   * This is used to do things like find users by their embed external_user_id. Or, find the user with
   * a given api3 client_id, etc. The 'credential_type' matchs the 'type' name of the various credential
   * types. It must be one of the values listed in the table below. The 'credential_id' is your unique Id
   * for the user and is specific to each type of credential.
   * 
   * An example using the Ruby sdk might look like:
   * 
   * `sdk.user_for_credential('embed', 'customer-4959425')`
   * 
   * This table shows the supported 'Credential Type' strings. The right column is for reference; it shows
   * which field in the given credential type is actually searched when finding a user with the supplied
   * 'credential_id'.
   * 
   * | Credential Types | Id Field Matched |
   * | ---------------- | ---------------- |
   * | email            | email            |
   * | google           | google_user_id   |
   * | saml             | saml_user_id     |
   * | oidc             | oidc_user_id     |
   * | ldap             | ldap_id          |
   * | api              | token            |
   * | api3             | client_id        |
   * | embed            | external_user_id |
   * | looker_openid    | email            |
   * 
   * NOTE: The 'api' credential type was only used with the legacy Looker query API and is no longer supported. The credential type for API you are currently looking at is 'api3'.
   * 
   * 
   */
  public void UserForCredential(UserForCredentialRequest request, StreamObserver<UserForCredentialResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Email/password login information for the specified user.
   */
  public void UserCredentialsEmail(UserCredentialsEmailRequest request, StreamObserver<UserCredentialsEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Email/password login information for the specified user.
   */
  public void CreateUserCredentialsEmail(CreateUserCredentialsEmailRequest request, StreamObserver<CreateUserCredentialsEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Email/password login information for the specified user.
   */
  public void UpdateUserCredentialsEmail(UpdateUserCredentialsEmailRequest request, StreamObserver<UpdateUserCredentialsEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Email/password login information for the specified user.
   */
  public void DeleteUserCredentialsEmail(DeleteUserCredentialsEmailRequest request, StreamObserver<DeleteUserCredentialsEmailResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Two-factor login information for the specified user.
   */
  public void UserCredentialsTotp(UserCredentialsTotpRequest request, StreamObserver<UserCredentialsTotpResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Two-factor login information for the specified user.
   */
  public void CreateUserCredentialsTotp(CreateUserCredentialsTotpRequest request, StreamObserver<CreateUserCredentialsTotpResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Two-factor login information for the specified user.
   */
  public void DeleteUserCredentialsTotp(DeleteUserCredentialsTotpRequest request, StreamObserver<DeleteUserCredentialsTotpResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### LDAP login information for the specified user.
   */
  public void UserCredentialsLdap(UserCredentialsLdapRequest request, StreamObserver<UserCredentialsLdapResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### LDAP login information for the specified user.
   */
  public void DeleteUserCredentialsLdap(DeleteUserCredentialsLdapRequest request, StreamObserver<DeleteUserCredentialsLdapResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Google authentication login information for the specified user.
   */
  public void UserCredentialsGoogle(UserCredentialsGoogleRequest request, StreamObserver<UserCredentialsGoogleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Google authentication login information for the specified user.
   */
  public void DeleteUserCredentialsGoogle(DeleteUserCredentialsGoogleRequest request, StreamObserver<DeleteUserCredentialsGoogleResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Saml authentication login information for the specified user.
   */
  public void UserCredentialsSaml(UserCredentialsSamlRequest request, StreamObserver<UserCredentialsSamlResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Saml authentication login information for the specified user.
   */
  public void DeleteUserCredentialsSaml(DeleteUserCredentialsSamlRequest request, StreamObserver<DeleteUserCredentialsSamlResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### OpenID Connect (OIDC) authentication login information for the specified user.
   */
  public void UserCredentialsOidc(UserCredentialsOidcRequest request, StreamObserver<UserCredentialsOidcResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### OpenID Connect (OIDC) authentication login information for the specified user.
   */
  public void DeleteUserCredentialsOidc(DeleteUserCredentialsOidcRequest request, StreamObserver<DeleteUserCredentialsOidcResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### API 3 login information for the specified user. This is for the newer API keys that can be added for any user.
   */
  public void UserCredentialsApi3(UserCredentialsApi3Request request, StreamObserver<UserCredentialsApi3Response> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### API 3 login information for the specified user. This is for the newer API keys that can be added for any user.
   */
  public void DeleteUserCredentialsApi3(DeleteUserCredentialsApi3Request request, StreamObserver<DeleteUserCredentialsApi3Response> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### API 3 login information for the specified user. This is for the newer API keys that can be added for any user.
   */
  public void AllUserCredentialsApi3s(AllUserCredentialsApi3sRequest request, StreamObserver<AllUserCredentialsApi3sResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### API 3 login information for the specified user. This is for the newer API keys that can be added for any user.
   */
  public void CreateUserCredentialsApi3(CreateUserCredentialsApi3Request request, StreamObserver<CreateUserCredentialsApi3Response> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Embed login information for the specified user.
   */
  public void UserCredentialsEmbed(UserCredentialsEmbedRequest request, StreamObserver<UserCredentialsEmbedResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Embed login information for the specified user.
   */
  public void DeleteUserCredentialsEmbed(DeleteUserCredentialsEmbedRequest request, StreamObserver<DeleteUserCredentialsEmbedResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Embed login information for the specified user.
   */
  public void AllUserCredentialsEmbeds(AllUserCredentialsEmbedsRequest request, StreamObserver<AllUserCredentialsEmbedsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Looker Openid login information for the specified user. Used by Looker Analysts.
   */
  public void UserCredentialsLookerOpenid(UserCredentialsLookerOpenidRequest request, StreamObserver<UserCredentialsLookerOpenidResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Looker Openid login information for the specified user. Used by Looker Analysts.
   */
  public void DeleteUserCredentialsLookerOpenid(DeleteUserCredentialsLookerOpenidRequest request, StreamObserver<DeleteUserCredentialsLookerOpenidResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Web login session for the specified user.
   */
  public void UserSession(UserSessionRequest request, StreamObserver<UserSessionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Web login session for the specified user.
   */
  public void DeleteUserSession(DeleteUserSessionRequest request, StreamObserver<DeleteUserSessionResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Web login session for the specified user.
   */
  public void AllUserSessions(AllUserSessionsRequest request, StreamObserver<AllUserSessionsResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a password reset token.
   * This will create a cryptographically secure random password reset token for the user.
   * If the user already has a password reset token then this invalidates the old token and creates a new one.
   * The token is expressed as the 'password_reset_url' of the user's email/password credential object.
   * This takes an optional 'expires' param to indicate if the new token should be an expiring token.
   * Tokens that expire are typically used for self-service password resets for existing users.
   * Invitation emails for new users typically are not set to expire.
   * The expire period is always 60 minutes when expires is enabled.
   * This method can be called with an empty body.
   * 
   */
  public void CreateUserCredentialsEmailPasswordReset(CreateUserCredentialsEmailPasswordResetRequest request, StreamObserver<CreateUserCredentialsEmailPasswordResetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about roles of a given user
   * 
   */
  public void UserRoles(UserRolesRequest request, StreamObserver<UserRolesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Set roles of the user with a specific id.
   * 
   */
  public void SetUserRoles(SetUserRolesRequest request, StreamObserver<SetUserRolesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get user attribute values for a given user.
   * 
   * Returns the values of specified user attributes (or all user attributes) for a certain user.
   * 
   * A value for each user attribute is searched for in the following locations, in this order:
   * 
   * 1. in the user's account information
   * 1. in groups that the user is a member of
   * 1. the default value of the user attribute
   * 
   * If more than one group has a value defined for a user attribute, the group with the lowest rank wins.
   * 
   * The response will only include user attributes for which values were found. Use `include_unset=true` to include
   * empty records for user attributes with no value.
   * 
   * The value of all hidden user attributes will be blank.
   * 
   */
  public void UserAttributeUserValues(UserAttributeUserValuesRequest request, StreamObserver<UserAttributeUserValuesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Store a custom value for a user attribute in a user's account settings.
   * 
   * Per-user user attribute values take precedence over group or default values.
   * 
   */
  public void SetUserAttributeUserValue(SetUserAttributeUserValueRequest request, StreamObserver<SetUserAttributeUserValueResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a user attribute value from a user's account settings.
   * 
   * After the user attribute value is deleted from the user's account settings, subsequent requests
   * for the user attribute value for this user will draw from the user's groups or the default
   * value of the user attribute. See [Get User Attribute Values](#!/User/user_attribute_user_values) for more
   * information about how user attribute values are resolved.
   * 
   */
  public void DeleteUserAttributeUserValue(DeleteUserAttributeUserValueRequest request, StreamObserver<DeleteUserAttributeUserValueResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Send a password reset token.
   * This will send a password reset email to the user. If a password reset token does not already exist
   * for this user, it will create one and then send it.
   * If the user has not yet set up their account, it will send a setup email to the user.
   * The URL sent in the email is expressed as the 'password_reset_url' of the user's email/password credential object.
   * Password reset URLs will expire in 60 minutes.
   * This method can be called with an empty body.
   * 
   */
  public void SendUserCredentialsEmailPasswordReset(SendUserCredentialsEmailPasswordResetRequest request, StreamObserver<SendUserCredentialsEmailPasswordResetResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion User: Manage Users

  //#region UserAttribute: Manage User Attributes

  /**   * ### Get information about all user attributes.
   * 
   */
  public void AllUserAttributes(AllUserAttributesRequest request, StreamObserver<AllUserAttributesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Create a new user attribute
   * 
   * Permission information for a user attribute is conveyed through the `can` and `user_can_edit` fields.
   * The `user_can_edit` field indicates whether an attribute is user-editable _anywhere_ in the application.
   * The `can` field gives more granular access information, with the `set_value` child field indicating whether
   * an attribute's value can be set by [Setting the User Attribute User Value](#!/User/set_user_attribute_user_value).
   * 
   * Note: `name` and `label` fields must be unique across all user attributes in the Looker instance.
   * Attempting to create a new user attribute with a name or label that duplicates an existing
   * user attribute will fail with a 422 error.
   * 
   */
  public void CreateUserAttribute(CreateUserAttributeRequest request, StreamObserver<CreateUserAttributeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get information about a user attribute.
   * 
   */
  public void UserAttribute(UserAttributeRequest request, StreamObserver<UserAttributeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Update a user attribute definition.
   * 
   */
  public void UpdateUserAttribute(UpdateUserAttributeRequest request, StreamObserver<UpdateUserAttributeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Delete a user attribute (admin only).
   * 
   */
  public void DeleteUserAttribute(DeleteUserAttributeRequest request, StreamObserver<DeleteUserAttributeResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Returns all values of a user attribute defined by user groups, in precedence order.
   * 
   * A user may be a member of multiple groups which define different values for a given user attribute.
   * The order of group-values in the response determines precedence for selecting which group-value applies
   * to a given user.  For more information, see [Set User Attribute Group Values](#!/UserAttribute/set_user_attribute_group_values).
   * 
   * Results will only include groups that the caller's user account has permission to see.
   * 
   */
  public void AllUserAttributeGroupValues(AllUserAttributeGroupValuesRequest request, StreamObserver<AllUserAttributeGroupValuesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Define values for a user attribute across a set of groups, in priority order.
   * 
   * This function defines all values for a user attribute defined by user groups. This is a global setting, potentially affecting
   * all users in the system. This function replaces any existing group value definitions for the indicated user attribute.
   * 
   * The value of a user attribute for a given user is determined by searching the following locations, in this order:
   * 
   * 1. the user's account settings
   * 2. the groups that the user is a member of
   * 3. the default value of the user attribute, if any
   * 
   * The user may be a member of multiple groups which define different values for that user attribute. The order of items in the group_values parameter
   * determines which group takes priority for that user. Lowest array index wins.
   * 
   * An alternate method to indicate the selection precedence of group-values is to assign numbers to the 'rank' property of each
   * group-value object in the array. Lowest 'rank' value wins. If you use this technique, you must assign a
   * rank value to every group-value object in the array.
   * 
   *   To set a user attribute value for a single user, see [Set User Attribute User Value](#!/User/set_user_attribute_user_value).
   * To set a user attribute value for all members of a group, see [Set User Attribute Group Value](#!/Group/update_user_attribute_group_value).
   * 
   */
  public void SetUserAttributeGroupValues(SetUserAttributeGroupValuesRequest request, StreamObserver<SetUserAttributeGroupValuesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion UserAttribute: Manage User Attributes

  //#region Workspace: Manage Workspaces

  /**   * ### Get All Workspaces
   * 
   * Returns all workspaces available to the calling user.
   * 
   */
  public void AllWorkspaces(AllWorkspacesRequest request, StreamObserver<AllWorkspacesResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  /**   * ### Get A Workspace
   * 
   * Returns information about a workspace such as the git status and selected branches
   * of all projects available to the caller's user account.
   * 
   * A workspace defines which versions of project files will be used to evaluate expressions
   * and operations that use model definitions - operations such as running queries or rendering dashboards.
   * Each project has its own git repository, and each project in a workspace may be configured to reference
   * particular branch or revision within their respective repositories.
   * 
   * There are two predefined workspaces available: "production" and "dev".
   * 
   * The production workspace is shared across all Looker users. Models in the production workspace are read-only.
   * Changing files in production is accomplished by modifying files in a git branch and using Pull Requests
   * to merge the changes from the dev branch into the production branch, and then telling
   * Looker to sync with production.
   * 
   * The dev workspace is local to each Looker user. Changes made to project/model files in the dev workspace only affect
   * that user, and only when the dev workspace is selected as the active workspace for the API session.
   * (See set_session_workspace()).
   * 
   * The dev workspace is NOT unique to an API session. Two applications accessing the Looker API using
   * the same user account will see the same files in the dev workspace. To avoid collisions between
   * API clients it's best to have each client login with API3 credentials for a different user account.
   * 
   * Changes made to files in a dev workspace are persistent across API sessions. It's a good
   * idea to commit any changes you've made to the git repository, but not strictly required. Your modified files
   * reside in a special user-specific directory on the Looker server and will still be there when you login in again
   * later and use update_session(workspace_id: "dev") to select the dev workspace for the new API session.
   * 
   */
  public void Workspace(WorkspaceRequest request, StreamObserver<WorkspaceResponse> responseObserver) {
    try {
      String inputJson = JsonFormat.printer().print(request);
      System.out.println(inputJson);
      responseObserver.onCompleted();
    } catch (InvalidProtocolBufferException e) {
      responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
    }
  }    
    

  //#endregion Workspace: Manage Workspaces
}