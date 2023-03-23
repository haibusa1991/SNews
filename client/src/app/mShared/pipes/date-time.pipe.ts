import {Pipe, PipeTransform} from '@angular/core';
import moment from "moment/moment";

@Pipe({
  name: 'datetime'
})
export class DateTimePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    // 2023-03-15 10:19:59.223590
    if(!moment(value,"YYYY-MM-DD HH:mm:ss").isValid()){
      return ''
    }

    return moment(value, "YYYY-MM-DD HH:mm:ss").locale('bg').format('DD MMMM YYYY HH:mm');
  }

}
