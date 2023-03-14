import {Pipe, PipeTransform} from '@angular/core';
import {DatePipe} from "@angular/common";

@Pipe({
  name: 'datetime'
})
export class DateTimePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {

    let datePipe = new DatePipe('en-US');
    let transformed = datePipe.transform(value, 'dd MMMM YYYY, HH:mm')!.toLowerCase();

    let translations:{[k:string]:string} = {
      'january':'януари',
      'february':'февруари',
      'march':'март',
      'april':'април',
      'may':'май',
      'june':'юни',
      'july':'юли',
      'august':'август',
      'september':'септември',
      'october':'октомври',
      'november':'ноември',
      'december':'декември'
    };

    let englishMonths = Object.keys(translations);

    for (let month of englishMonths) {
      if(transformed.match(month)) {
        return transformed.replace(month,translations[month]);
      }
    }

    return transformed;
  }

}
