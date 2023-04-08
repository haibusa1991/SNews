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
  thumbnail: string,
  published: string
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
    'USER',
    'MODERATOR',
    'ADMINISTRATOR',
  ],
  avatar: string,
  defaultAvatarColor: string;
  email: string
}

export type RegisterResponse = {
  isEmailTaken: boolean,
  isUsernameTaken: boolean,
  isRegistrationClosed: boolean
}

export type Article = {
  heading: string,
  published: string,
  image: string,
  imageSource: string,
  content: string[],
  author: string
  categories: string[]
}

export type ServerConfiguration = {
  enableNewUserRegistration: boolean
}
