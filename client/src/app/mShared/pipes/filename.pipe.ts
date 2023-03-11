import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filename'
})
export class FilenamePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    return value.substring(value.lastIndexOf('\\') + 1);
  }

}
