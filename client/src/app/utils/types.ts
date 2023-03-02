export type Icon = {
  path: string,
  viewBox: string;
};

export type NamedLink = {
  href: string,
  name: string
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
    'admin',
  ]
}
