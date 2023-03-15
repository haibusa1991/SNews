import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router, RouterEvent} from "@angular/router";
import {Article, ArticleOverviewData, ArticleTag} from "../../utils/types";
import {ArticleService} from "../../core/article-service/article.service";
import {articleCategories, articleCategoriesHref} from "../../utils/snewsConstants";
import moment from "moment";
import {filter, map, tap} from "rxjs";
import {UserService} from "../../core/user-service/user.service";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  article: Article = {
    articleTags: [],
    author: "",
    content: [],
    heading: "",
    href: "",
    picture: "",
    pictureSource: "",
    published: ""
  };
  articleTags: ArticleTag[] = [];

  //todo replace with proper implementation; Count must be multiple of 2
  relatedArticles: ArticleOverviewData[] = [
    {
      heading: '"Вектор за атака1". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      published: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака2". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      published: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака3". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      published: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
    {
      heading: '"Вектор за атака4". Как София даде милиони за китайски камери в градския транспорт',
      href: '/articles/article-url-goes-here',
      published: '02 Февруари 2023 17:33',
      thumbnailUrl: '/assets/placeholders/article-overview-placeholder.png'
    },
  ]

  isOldArticle: boolean = false
  isShortArticle: boolean = false
  oldArticleWarning: string = '';

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private articleService: ArticleService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    let articleHref = this.activatedRoute.snapshot.paramMap.get('articleHref');

    this.articleService.getArticle$(articleHref!).subscribe(article => {
      this.article = article;
      this.checkIfOldArticle();

      if (this.article.content.length === 1) {
        this.isShortArticle = true;
      }

      for (let inputTag of article.articleTags) {
        this.articleTags.push({
          href: articleCategoriesHref[inputTag],
          tag: articleCategories[inputTag]
        });
      }
    });

    this.router.events.pipe(
      filter((e): e is NavigationStart => e instanceof NavigationStart),
      tap(() => {
        this.userService.setUrlBeforeLogin('/' + this.activatedRoute.snapshot.url.join('/'));
      })
    ).subscribe();
  }

  onNavigate(href: string) {
    this.router.navigateByUrl(href)
  }

  checkIfOldArticle() {
    let articleDate = this.article.published;
    let now = moment(new Date());

    if (now.isAfter(moment(articleDate, 'YYYY-MM-DD HH:mm:ss').add(1, "month"))) {
      this.isOldArticle = true;
      this.oldArticleWarning = 'Тази статия е публикувана преди повече от месец.';

      if (now.isAfter(moment(articleDate, 'YYYY-MM-DD HH:mm:ss').add(1, 'year'))) {
        this.oldArticleWarning = 'Тази статия е публикувана преди повече от година.';
      } else if (now.isAfter(moment(articleDate, 'YYYY-MM-DD HH:mm:ss').add(6, 'month'))) {
        this.oldArticleWarning = 'Тази статия е публикувана преди повече от 6 месеца.';
      } else if (now.isAfter(moment(articleDate, 'YYYY-MM-DD HH:mm:ss').add(3, 'month'))) {
        this.oldArticleWarning = 'Тази статия е публикувана преди повече 3 месеца';
      }
    }
  }
}
