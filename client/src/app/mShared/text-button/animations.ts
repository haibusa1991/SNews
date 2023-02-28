import {animate, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_DURATION = '125ms'

export const rotateArrow =
  trigger('rotateArrow', [
    state('open', style({
      'transform': 'rotate(180deg)',
      'transform-origin':'0.34rem 0.95rem'
    })),
    state('closed', style({
      'transform': 'rotate(0deg)',
    })),
    transition('* => *', [
      animate(ANIMATION_DURATION)
    ])
  ]);

