import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EventProviderService {
  private subject: Subject<boolean> = new Subject<boolean>();

  constructor() { }

  emitBackgroundClick() {
    this.subject.next(true);
  }

  $backgroundClick() {
    return new Observable<boolean>(e => {
      this.subject.subscribe(status => e.next(status as boolean));
    })
  }
}
