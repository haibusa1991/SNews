import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router, RouterEvent} from "@angular/router";
import {Article, ArticleOverviewData, ArticleTag, User} from "../../utils/types";
import {ArticleService} from "../../core/article-service/article.service";
import {articleCategories, articleCategoriesHref} from "../../utils/snewsConstants";
import moment from "moment";
import {filter, map, tap} from "rxjs";
import {UserService} from "../../core/user-service/user.service";
import {articleEndpoints} from "../../../environments/environment";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  article!: Article;
  articleCategories: ArticleTag[] = [];
  imagePath = articleEndpoints['imagePath']

  //todo replace with proper implementation; Count must be multiple of 2
  relatedArticles!: ArticleOverviewData[];

  isOldArticle: boolean = false
  currentUser: User | null = null;
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

      for (let category of article.categories) {
        this.articleCategories.push({
          href: articleCategoriesHref[category],
          tag: articleCategories[category]
        });
      }

      // TODO refactor - remove double subscription
      // debugger
      this.articleService
        .getRelatedArticles$(this.article.categories[0])
        .subscribe(relatedArticles => this.relatedArticles=relatedArticles);
      });


    this.userService.validateSession();
    this.userService.getCurrentUser$().subscribe(user => this.currentUser = user);

    this.router.events.pipe(
      filter((e): e is NavigationStart => e instanceof NavigationStart),
      tap(() => {
        this.userService.setUrlBeforeLogin('/news/' + this.activatedRoute.snapshot.url.join('/'));
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
