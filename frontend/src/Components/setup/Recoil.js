import { atom } from 'recoil';
 
// Atom for managing user state
export const userState = atom({
  key: 'userState', // unique ID (with respect to other atoms/selectors)
  default: '', // default value (aka initial value)
});
 
// Atom for managing cart state
export const cartState = atom({
  key: 'cartState', // unique ID (with respect to other atoms/selectors)
  default: [], // Initialize with an empty array
});