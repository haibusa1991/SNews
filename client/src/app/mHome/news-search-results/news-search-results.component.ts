import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ArticleService} from "../../core/article-service/article.service";
import {ArticleOverviewData} from "../../utils/types";

@Component({
  selector: 'app-news-search-results',
  templateUrl: './news-search-results.component.html',
  styleUrls: ['./news-search-results.component.scss']
})
export class NewsSearchResultsComponent implements OnInit {

  articles: ArticleOverviewData[] = [];

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private articleService: ArticleService) {
  }


  ngOnInit(): void {
    let category = this.activatedRoute.snapshot.paramMap.get('category');
    this.articleService.getTodayArticles$().subscribe(res => this.articles = res);
  }

  onNavigate(url: string) {

  }
}
