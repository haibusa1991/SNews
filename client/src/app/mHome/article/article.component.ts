import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Article, ArticleContent, ArticleOverviewData, ArticleTag} from "../../utils/types";
import {ArticleService} from "../../core/article-service/article.service";
import {articleCategories, articleCategoriesHref} from "../../utils/snewsConstants";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  article!: Article;
  articleTags: ArticleTag[] = [];

  //todo replace with proper implementation; Count must be multiple of 2
  relatedArticles: ArticleOverviewData[] = [
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

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private articleService: ArticleService) {
  }

  ngOnInit(): void {
    this.checkIfOldArticle();


    let articleHref = this.activatedRoute.snapshot.paramMap.get('articleHref');
    this.articleService.getArticle$(articleHref!).subscribe(article => {
      this.article = article;

      for (let inputTag of article.articleTags) {
        this.articleTags.push({
          href: articleCategoriesHref[inputTag],
          tag: articleCategories[inputTag]
        })
      }
    });
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
