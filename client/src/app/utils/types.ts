export type Icon = {
  path: string,
  viewBox: string;
};
//todo check if can be removed
export type NamedLink = {
  href: string,
  name: string
}

export type ArticleTag = {
  tag: string,
  href: string
}

export type ArticleOverviewData = {
  href: string,
  thumbnailUrl: string,
  publishDate: string
  heading: string
}

export type ArticleContent = {
  id: string,
  heading: string,
  publishDate: string,
  imageUrl: string,
  imageSource: string,
  content: string[],
  author: string,
  categories: NamedLink[]
}

export type User = {
  'username': string,
  roles: [
    'user',
    'moderator',
    'administrator',
  ]
}

export type RegisterResponse = {
  isEmailTaken:boolean,
  isUsernameTaken:boolean
}

export type Article = {
  href:string,
  heading:string,
  published:string,
  picture:string,
  pictureSource:string,
  content:string[],
  author:string
  articleTags:string[]
}
