import {animate, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_DURATION = '125ms'

export const toggleXMark =
  trigger('toggleXMark', [
    state('open', style({
      'transform': 'translateY(-100%)'
    })),
    state('closed', style({
      'transform': 'translateY(0)'
    })),
    transition('* => *', [
      animate(ANIMATION_DURATION)
    ])
  ]);

export const toggleMain =
  trigger('toggleMain', [
    state('open', style({
      'transform': 'translateY(-100%)'
    })),
    state('closed', style({
      'transform': 'translateY(0)'
    })),
    transition('* => *', [
      animate(ANIMATION_DURATION)
    ])
  ]);
