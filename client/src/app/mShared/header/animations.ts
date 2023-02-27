import {animate, state, style, transition, trigger} from "@angular/animations";

const ANIMATION_DURATION = '150ms'

export const menuToggle =
  trigger('menuToggle', [
    state('open', style({ 'transform' : 'translateY(100%)' })),
    transition(':enter', [
      style({ 'transform' : 'translateY(0)' }),
      animate(ANIMATION_DURATION)
    ]),
    transition(':leave', [
      animate(ANIMATION_DURATION, style({'transform' : 'translateY(0)' }))
    ])
  ]);
