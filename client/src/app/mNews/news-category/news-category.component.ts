import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ArticleService} from "../../core/article-service/article.service";
import {ArticleOverviewData} from "../../utils/types";
import {articleCategories} from "../../utils/snewsConstants";
import {switchMap} from "rxjs";

@Component({
  selector: 'app-news-search-results',
  templateUrl: './news-category.component.html',
  styleUrls: ['./news-category.component.scss']
})
export class NewsCategory implements OnInit {
  NO_NEWS_TEXT = 'Все още няма публикувани новини.';


  articles: ArticleOverviewData[] = [];
  categoryName: String = '';
  noNews: string = '';
  isLoading: boolean = true;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private articleService: ArticleService) {
  }


  ngOnInit(): void {

    this.activatedRoute.paramMap.pipe(
      switchMap(paramMap => {
        this.isLoading = true;
        this.articles = [];
        this.noNews = ''
        let category: string = paramMap.get('category')!

        let isValidCategory: boolean = Object.keys(articleCategories).join('|').toUpperCase().includes(category.toUpperCase());

        if (!isValidCategory) {
          this.router.navigateByUrl('/404')
        }

        this.categoryName = articleCategories[category.toUpperCase()].toLowerCase();
        return this.articleService.getArticlesByCategory$(paramMap.get('category')!)
      })
    )
      .subscribe(res => {
        this.isLoading = false;
        this.articles = res;
        if (this.articles.length === 0) {
          this.noNews = this.NO_NEWS_TEXT;
        }
      });
  }
}
