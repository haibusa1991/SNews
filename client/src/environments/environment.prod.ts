export const environment = {
  production: true
};

export const userEndpoints = {
  'login': 'https://snews.azurewebsites.net/user/login',
  'register': 'https://snews.azurewebsites.net/user/register',
  'logout': 'https://snews.azurewebsites.net/user/logout',
  'getUsername' : 'https://snews.azurewebsites.net/user/username',
  'getUser':'https://snews.azurewebsites.net/user/user',
  'forgottenPassword':'https://snews.azurewebsites.net/user/forgotten-password',
  'passwordReset':'https://snews.azurewebsites.net/user/reset-password',
  'allUserComments':'https://snews.azurewebsites.net/user/comments',
  'uploadAvatar':'https://snews.azurewebsites.net/user/upload-avatar',
  'removeAvatar':'https://snews.azurewebsites.net/user/remove-avatar',
  'downloadAvatar':'https://snews.azurewebsites.net/images/',
  'changePassword':'https://snews.azurewebsites.net/user/change-password',
  'changeEmail':'https://snews.azurewebsites.net/user/change-email',
  'updateAuthorities':'https://snews.azurewebsites.net/user/update-authority',
}

export const articleEndpoints = {
  'newArticle': 'https://snews.azurewebsites.net/article/new-article',
  'articleCategories':'https://snews.azurewebsites.net/article/article-categories',
  'articles':'https://snews.azurewebsites.net/article',
  'homePageArticles':'https://snews.azurewebsites.net/article/home-articles',
  'articleByCategory':'https://snews.azurewebsites.net/article/article-category',
  'thumbnailPath':'https://snews.azurewebsites.net/images/',
  'imagePath':'https://snews.azurewebsites.net/images/',
  'relatedArticles':'https://snews.azurewebsites.net/article/related-articles',
}

export const queryEndpoints:{[k:string]:string} = {
  'findUser': 'https://snews.azurewebsites.net/query/user/',
  'findArticles': 'https://snews.azurewebsites.net/query'
}

export const configurationEndpoints:{[k:string]:string} = {
  'modifySetting': 'https://snews.azurewebsites.net/configuration/modify-setting',
  'setState': 'https://snews.azurewebsites.net/configuration/set-state',
  'getState': 'https://snews.azurewebsites.net/configuration/get-state',
  'canRegister': 'https://snews.azurewebsites.net/configuration/can-register',
}
