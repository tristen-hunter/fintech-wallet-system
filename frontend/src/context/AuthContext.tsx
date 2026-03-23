import type UserfetchDTO from "../types/UserFetchDTO";

interface AuthContextType {
    user: UserfetchDTO | null;
    loading: boolean,
    loginUser: (userData: UserfetchDTO) => void;
    logoutUser: () => void;
}