// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false
};

export const apiEndpoints = {
  'articleContent': 'api/api/articleContent',
  'csrf': 'api/api/csrf',
  'testPost': 'api/api/testPost'
}

export const userEndpoints = {
  'login': 'api/user/login',
  'register': 'api/user/register',
  'logout': 'api/user/logout',
  'csrf': apiEndpoints['csrf'],
  'getUsername' : '/api/user/username',
  'getUser':'/api/user/user',
  'forgottenPassword':'/api/user/forgotten-password',
  'passwordReset':'/api/user/reset-password'
}


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
