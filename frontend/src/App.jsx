import { useEffect } from "react";
import { todoApi } from "./api";

const App = () => {
  useEffect(() => {
    const test = async () => {
      const data = todoApi.getAllTodos();

      console.log(data);
    };

    test();
  }, []);
  return <div>App</div>;
};

export default App;
