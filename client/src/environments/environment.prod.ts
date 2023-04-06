export const environment = {
  production: true
};

export const apiEndpoints = {
  'articleContent': 'http://localhost:8080/api/articleContent',
  'csrf': 'http://localhost:8080/api/csrf',
  'testPost': 'http://localhost:8080/api/testPost',
  'search': 'http://localhost:8080/api/search',
}

export const userEndpoints = {
  'login': 'http://localhost:8080/user/login',
  'register': 'http://localhost:8080/user/register',
  'logout': 'http://localhost:8080/user/logout',
  'csrf': apiEndpoints['csrf'],
  'getUsername' : 'http://localhost:8080/user/username',
  'getUser':'http://localhost:8080/user/user',
  'forgottenPassword':'http://localhost:8080/user/forgotten-password',
  'passwordReset':'http://localhost:8080/user/reset-password',
  'allUserComments':'http://localhost:8080/user/comments',
  'uploadAvatar':'http://localhost:8080/user/upload-avatar',
  'removeAvatar':'http://localhost:8080/user/remove-avatar',
  'downloadAvatar':'http://localhost:8080/avatars/',
  'changePassword':'http://localhost:8080/user/change-password',
  'changeEmail':'http://localhost:8080/user/change-email',
  'updateAuthorities':'http://localhost:8080/user/update-authority',
}

export const articleEndpoints = {
  'newArticle': 'http://localhost:8080/article/new-article',
  'articleCategories':'http://localhost:8080/article/article-categories',
  'articles':'http://localhost:8080/api/article',
  'homePageArticles':'http://localhost:8080/api/article/home-articles',
  'articleByCategory':'http://localhost:8080/api/news',
  'thumbnailPath':'http://localhost:8080/thumbnails/',
  'imagePath':'http://localhost:8080/images/',
}

export const queryEndpoints:{[k:string]:string} = {
  'findUser': 'http://localhost:8080/query/user/',
}

export const configurationEndpoints:{[k:string]:string} = {
  'modifySetting': 'http://localhost:8080/configuration/modify-setting',
  'setState': 'http://localhost:8080/configuration/set-state',
  'getState': 'http://localhost:8080/configuration/get-state',
  'canRegister': 'http://localhost:8080/configuration/can-register',
}
