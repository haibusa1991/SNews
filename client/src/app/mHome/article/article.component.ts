import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ArticleContent, ArticleOverviewData} from "../../types/types";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  //todo replace with proper implementation
  articleContent: ArticleContent = {
    heading: '"Вектор за атака". Как София даде милиони за китайски камери в градския транспорт',
    imageSource: 'RFE/RL',
    content: [
      'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil',
      'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil',
      'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab ad aspernatur facere incidunt, modi recusandae. Alias aliquid dignissimos et iste necessitatibus nisi officia sed ullam unde, voluptate. Adipisci nihil',
    ],
    author: 'RFE/RL',
    id: 'article-id-goes-here',
    imageUrl: "",
    publishDate: "02 Февруари 2023 17:33",
    categories: [
      {href: '/news/analyses', name: 'Анализи'},
      {href: '/news/politics', name: 'Политика'}
    ]
  }

  //todo replace with proper implementation; Count must be multiple of 2

  relatedArticles:ArticleOverviewData[] = [
    {
      heading: '"Вектор за атака1". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      publishDate: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака2". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      publishDate: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака3". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      publishDate: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака4". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      publishDate: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
  ]

  isOldArticle: boolean = false
  oldArticleWarning: string = '';

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.checkIfOldArticle();
  }

  onNavigate(href: string) {
    this.router.navigateByUrl(href)
  }

  checkIfOldArticle() {
    //todo implement - check if article is older than 1/3/6/12 months and show warning
    // Тази статия е публикувана преди повече от 3 месеца.
    // Тази статия е публикувана преди повече от 1 година.

    this.isOldArticle = true;
    this.oldArticleWarning = 'Тази статия е публикувана преди повече от 3 месеца.'
  }
}
