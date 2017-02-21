using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TestLibrary
{
    [TestClass]
    public class Class1
    {
        [TestMethod]
        public void testTrue()
        {
            Assert.IsTrue(true);
        }

        [TestMethod]
        public void testFalse()
        {
            Assert.IsFalse(false);
        }
    }
}
