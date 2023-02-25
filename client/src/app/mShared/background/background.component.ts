import {Component, HostListener, OnInit} from '@angular/core';
import {EventProviderService} from "../../core/event-provider.service";

@Component({
  selector: 'app-background',
  templateUrl: './background.component.html',
  styleUrls: ['./background.component.scss']
})
export class BackgroundComponent implements OnInit {
  private MIN_SWIPE_DISTANCE = 10; //swipe must be at least this many pixels long

  private touchData = {
    startX: 0,
    startY: 0,
    deltaX: 0,
    deltaY: 0,
  };


  constructor(private eventProvider:EventProviderService) { }

  ngOnInit(): void {
  }

  onBackgroundClick() {
    this.eventProvider.emitBackgroundClick();
  }



  @HostListener('touchstart', ['$event', 'passive:true'])
  onTouchStart(event: TouchEvent) {
    let touch = event.touches[0]

    this.touchData.startX = touch.pageX;
    this.touchData.startY = touch.pageY;
  }

  @HostListener('touchend', ['$event'])
  onTouchEnd(event: TouchEvent) {
    let touch = event.changedTouches[0];

    this.touchData.deltaX = touch.pageX - this.touchData.startX;
    this.touchData.deltaY = touch.pageY - this.touchData.startY;
    this.onSwipe();
  }

  onSwipe() {
    if (
      Math.abs(this.touchData.deltaX) > this.MIN_SWIPE_DISTANCE
      || Math.abs(this.touchData.deltaY) > this.MIN_SWIPE_DISTANCE) {
      this.onBackgroundClick();
    }
  }
}
