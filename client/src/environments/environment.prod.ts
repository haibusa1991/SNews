export const environment = {
  production: true
};

export const apiEndpoints = {
  'articleContent': 'http://localhost:8080/api/articleContent',
  'csrf': 'http://localhost:8080/api/csrf',
  'testPost': 'http://localhost:8080/api/testPost'
}

export const userEndpoints = {
  'login': 'http://localhost:8080/user/login',
  'register': 'http://localhost:8080/user/register',
  'logout': 'http://localhost:8080/user/logout',
  'csrf': apiEndpoints['csrf'],
  'getUsername' : 'http://localhost:8080/user/username',
  'getUser':'http://localhost:8080/user/user',
  'forgottenPassword':'http://localhost:8080/user/forgotten-password',
  'passwordReset':'http://localhost:8080/user/reset-password'
}

export const articleEndpoints = {
  'newArticle': 'http://localhost:8080/article/new-article',
}


