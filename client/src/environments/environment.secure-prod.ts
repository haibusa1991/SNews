export const environment = {
  production: false
};

export const apiEndpoints = {
  'articleContent': 'https://localhost:8080/api/articleContent',
  'csrf': 'https://localhost:8080/api/csrf',
  'testPost': 'https://localhost:8080/api/testPost',
  'search': 'https://localhost:8080/api/search',
}

export const userEndpoints = {
  'login': 'https://localhost:8080/user/login',
  'register': 'https://localhost:8080/user/register',
  'logout': 'https://localhost:8080/user/logout',
  'csrf': apiEndpoints['csrf'],
  'getUsername' : 'https://localhost:8080/user/username',
  'getUser':'https://localhost:8080/user/user',
  'forgottenPassword':'https://localhost:8080/user/forgotten-password',
  'passwordReset':'https://localhost:8080/user/reset-password',
  'allUserComments':'https://localhost:8080/user/comments'
}

export const articleEndpoints = {
  'newArticle': 'https://localhost:8080/article/new-article',
  'articleCategories':'https://localhost:8080/article/article-categories',
  'articles':'https://localhost:8080/api/article',
  'homePageArticles':'https://localhost:8080/api/article/home-articles',
  'articleByCategory':'https://localhost:8080/api/news',
  'thumbnailPath':'https://localhost:8080/thumbnails/',
  'imagePath':'https://localhost:8080/images/',
}


