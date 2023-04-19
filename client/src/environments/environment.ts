export const environment = {
  production: false
};

export const userEndpoints = {
  'login': 'api/user/login',
  'register': 'api/user/register',
  'logout': 'api/user/logout',
  'getUsername' : '/api/user/username',
  'getUser':'/api/user/user',
  'forgottenPassword':'/api/user/forgotten-password',
  'passwordReset':'/api/user/reset-password',
  'allUserComments':'/api/user/comments',
  'uploadAvatar':'/api/user/upload-avatar',
  'removeAvatar':'/api/user/remove-avatar',
  'downloadAvatar':'/api/images/',
  'changePassword':'/api/user/change-password',
  'changeEmail':'/api/user/change-email',
  'updateAuthorities':'/api/user/update-authority',
}

export const articleEndpoints = {
  'newArticle': '/api/article/new-article',
  'articleCategories':'api/article/article-categories',
  'articles':'/api/article',
  'homePageArticles':'/api/article/home-articles',
  'articleByCategory':'/api/article/article-category',
  'thumbnailPath':'/api/images/',
  'imagePath':'/api/images/',
  'relatedArticles':'/api/article/related-articles',
}

export const queryEndpoints:{[k:string]:string} = {
  'findUser': '/api/query/user/',
  'findArticles': '/api/query/'
}

export const configurationEndpoints:{[k:string]:string} = {
  'modifySetting': '/api/configuration/modify-setting',
  'setState': '/api/configuration/set-state',
  'getState': '/api/configuration/get-state',
  'canRegister': '/api/configuration/can-register',
}
