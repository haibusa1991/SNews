import {Component, Input, OnInit} from '@angular/core';
import {ArticleOverviewData} from "../../utils/types";
import {articleEndpoints} from "../../../environments/environment";

@Component({
  selector: 'app-article-overview',
  templateUrl: './article-overview.component.html',
  styleUrls: ['./article-overview.component.scss']
})
export class ArticleOverviewComponent implements OnInit {
  thumbnailPath = articleEndpoints['thumbnailPath'];


  @Input()
  isSmallHeading: boolean = false
  @Input()
  articleOverviewData!: ArticleOverviewData

  constructor() {
  }

  ngOnInit(): void {
  }

}
