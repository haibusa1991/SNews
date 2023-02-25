import {animate, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_DURATION = '150ms'

export const menuToggle =
  trigger('menuToggle', [
    state('closed', style({
      'transform': 'translateY(0)',
      // 'z-index':'-100'
    })),
    state('open', style({
      'transform': 'translateY(100%)',
      // 'z-index':'100'
    })),
    transition('* => *', [
      animate(ANIMATION_DURATION)
    ])
  ]);
